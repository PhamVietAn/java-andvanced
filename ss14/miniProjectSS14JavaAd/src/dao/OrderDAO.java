package dao;

import utils.DBConnection;

import java.sql.*;
import java.util.Map;

/**
 * DAO class cho thao tác với bảng Orders và Order_Details
 * Sử dụng PreparedStatement và Batch Processing
 */
public class OrderDAO {

    /**
     * TẠO ĐƠN HÀNG MỚI
     * Method này PHẢI được gọi trong một Transaction
     *
     * @param conn - Connection từ transaction
     * @param userId - id người dùng đặt hàng
     * @return id của đơn hàng vừa tạo, hoặc -1 nếu thất bại
     */
    public int createOrder(Connection conn, int userId) throws SQLException {
        String sql = "INSERT INTO orders(user_id) VALUES(?)";

        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, userId);
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return -1;
    }

    /**
     * THÊM CHI TIẾT ĐƠN HÀNG (SỬ DỤNG BATCH PROCESSING)
     *
     * Áp dụng Batch Processing để tối ưu hiệu năng khi insert nhiều bản ghi cùng lúc
     * Thay vì thực hiện N lần executeUpdate(), chỉ cần 1 lần executeBatch()
     *
     * Method này PHẢI được gọi trong một Transaction
     *
     * @param conn - Connection từ transaction
     * @param orderId - id đơn hàng
     * @param items - Map<productId, quantity>
     * @param productDAO - DAO để lấy giá sản phẩm
     */
    public void insertOrderDetails(Connection conn, int orderId, Map<Integer, Integer> items, dao.ProductDAO productDAO) throws SQLException {
        String sql = "INSERT INTO order_details(order_id, product_id, quantity, price) VALUES(?,?,?,?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // Duyệt qua từng sản phẩm trong đơn hàng
            for (Map.Entry<Integer, Integer> entry : items.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Lấy giá hiện tại của sản phẩm
                double price = productDAO.getPrice(conn, productId);

                ps.setInt(1, orderId);
                ps.setInt(2, productId);
                ps.setInt(3, quantity);
                ps.setDouble(4, price);

                // Thêm vào batch thay vì execute ngay
                ps.addBatch();
            }

            // Execute tất cả các câu lệnh INSERT cùng một lúc
            ps.executeBatch();
        }
    }
}
