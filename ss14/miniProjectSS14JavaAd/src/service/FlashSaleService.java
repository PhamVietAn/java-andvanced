package service;

import dao.OrderDAO;
import dao.ProductDAO;
import utils.DBConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * Service class xử lý logic nghiệp vụ cho Flash Sale
 * ĐÂY LÀ PHẦN CỐT LÕI CỦA HỆ THỐNG - XỬ LÝ TRANSACTION ĐỂ TRÁNH OVERSELLING
 */
public class FlashSaleService {

    private ProductDAO productDAO = new ProductDAO();
    private OrderDAO orderDAO = new OrderDAO();

    /**
     * ĐẶT HÀNG TRONG FLASH SALE (QUAN TRỌNG NHẤT!)
     *
     * Quá trình đặt hàng được thực hiện theo quy trình 3 bước ATOMIC:
     * 1. Trừ số lượng tồn kho (decreaseStock)
     * 2. Tạo đơn hàng (createOrder)
     * 3. Thêm chi tiết đơn hàng (insertOrderDetails)
     *
     * NẾU BẤT KỲ BƯỚC NÀO THẤT BẠI → ROLLBACK TẤT CẢ
     *
     * ===================== KIẾN THỨC TRANSACTION QUAN TRỌNG =====================
     *
     * 1. ACID Properties:
     *    - Atomicity: Tất cả hoặc không gì (all or nothing)
     *    - Consistency: Dữ liệu luôn nhất quán
     *    - Isolation: Các transaction không ảnh hưởng lẫn nhau
     *    - Durability: Khi commit, dữ liệu được lưu vĩnh viễn
     *
     * 2. AutoCommit Mode:
     *    - Mặc định: autoCommit = true (mỗi câu lệnh SQL tự động commit)
     *    - Để dùng transaction: PHẢI set autoCommit = false
     *
     * 3. Transaction Isolation Levels (từ thấp đến cao):
     *    - READ_UNCOMMITTED: Đọc được dữ liệu chưa commit (dirty read)
     *    - READ_COMMITTED: Chỉ đọc dữ liệu đã commit (non-repeatable read)
     *    - REPEATABLE_READ: Đọc nhất quán trong 1 transaction (phantom read)
     *    - SERIALIZABLE: Mức cao nhất, tránh mọi vấn đề đồng thời (CHẬM NHẤT)
     *
     * 4. Tại sao dùng SERIALIZABLE cho Flash Sale?
     *    - Tránh "Lost Update": 2 thread cùng đọc stock=1, cả 2 đều nghĩ còn hàng
     *    - Đảm bảo stock KHÔNG BAO GIỜ bị âm
     *    - Trade-off: Hiệu năng thấp hơn, nhưng DATA INTEGRITY là ưu tiên số 1
     *
     * 5. Tại sao mỗi thread phải có connection riêng?
     *    - Connection trong JDBC KHÔNG thread-safe
     *    - Mỗi transaction cần 1 connection độc lập
     *    - Dùng chung connection → Conflict, Rollback sai, Deadlock
     *
     * =============================================================================
     *
     * @param userId - id người dùng đặt hàng
     * @param items - Map<productId, quantity> danh sách sản phẩm muốn mua
     */
    public void placeOrder(int userId, Map<Integer, Integer> items) {

        // Mỗi thread PHẢI có connection riêng (QUAN TRỌNG!)
        Connection conn = null;

        try {
            // ============== BƯỚC 0: TẠO CONNECTION MỚI CHO TRANSACTION ==============
            // KHÔNG dùng getConnection() (shared) mà dùng getNewConnection() (riêng)
            conn = DBConnection.getNewConnection();

            // ============== BƯỚC 1: TẮT AUTOCOMMIT ==============
            // Mặc định JDBC có autoCommit=true (mỗi câu lệnh tự động commit)
            // Để dùng Transaction, PHẢI tắt autoCommit
            conn.setAutoCommit(false);

            // ============== BƯỚC 2: ĐẶT MỨC ĐỘ CÔ LẬP (ISOLATION LEVEL) ==============
            // SERIALIZABLE: Mức cao nhất, tránh Lost Update, Phantom Read
            // Đảm bảo không có 2 transaction cùng sửa 1 dòng dữ liệu
            conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            // ============== BƯỚC 3: TRỪ SỐ LƯỢNG TỒN KHO ==============
            // Câu lệnh SQL: UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?
            // Điều kiện "AND stock >= ?" đảm bảo chỉ trừ khi đủ hàng
            for (Map.Entry<Integer, Integer> item : items.entrySet()) {
                int productId = item.getKey();
                int quantity = item.getValue();

                boolean success = productDAO.decreaseStock(conn, productId, quantity);

                if (!success) {
                    // Không đủ hàng → Ném exception để trigger rollback
                    throw new RuntimeException("Hết hàng cho sản phẩm ID: " + productId);
                }
            }

            // ============== BƯỚC 4: TẠO ĐƠN HÀNG ==============
            int orderId = orderDAO.createOrder(conn, userId);

            if (orderId == -1) {
                throw new RuntimeException("Không thể tạo đơn hàng");
            }

            // ============== BƯỚC 5: THÊM CHI TIẾT ĐƠN HÀNG (BATCH PROCESSING) ==============
            orderDAO.insertOrderDetails(conn, orderId, items, productDAO);

            // ============== BƯỚC 6: COMMIT TRANSACTION ==============
            // Nếu đến đây mà không có lỗi → Tất cả thành công → COMMIT
            conn.commit();

            System.out.println("✓ THÀNH CÔNG - User " + userId + " đã đặt hàng (Order ID: " + orderId + ")");

        } catch (Exception e) {
            // ============== XỬ LÝ LỖI: ROLLBACK ==============
            // Nếu có BẤT KỲ lỗi nào xảy ra → Hoàn tác TẤT CẢ thay đổi
            try {
                if (conn != null) {
                    conn.rollback();
                    System.out.println("✗ THẤT BẠI - User " + userId + " - Lý do: " + e.getMessage());
                }
            } catch (SQLException rollbackEx) {
                System.err.println("Lỗi nghiêm trọng khi rollback: " + rollbackEx.getMessage());
                rollbackEx.printStackTrace();
            }

        } finally {
            // ============== DỌN DẸP: RESET AUTOCOMMIT VÀ ĐÓNG CONNECTION ==============
            try {
                if (conn != null) {
                    // Khôi phục autoCommit về true (quan trọng nếu connection được reuse)
                    conn.setAutoCommit(true);

                    // Đóng connection (vì đây là connection riêng, PHẢI đóng)
                    DBConnection.closeConnection(conn);
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng connection: " + e.getMessage());
            }
        }
    }

    /**
     * KIỂM TRA SỐ LƯỢNG TỒN KHO CỦA MỘT SẢN PHẨM
     * (Phương thức bổ trợ cho việc hiển thị thông tin)
     */
    public int checkStock(int productId) throws SQLException {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            return productDAO.getStock(conn, productId);
        } finally {
            // Không cần đóng vì dùng shared connection
        }
    }
}
