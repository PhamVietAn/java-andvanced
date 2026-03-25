package dao;

import utils.DBConnection;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * DAO class cho các chức năng báo cáo và thống kê
 * Sử dụng CallableStatement để gọi Stored Procedures
 */
public class ReportDAO {

    /**
     * LẤY TOP 5 NGƯỜI MUA NHIỀU NHẤT (SỬ DỤNG CALLABLE STATEMENT)
     *
     * Gọi Stored Procedure: GetTopBuyers()
     * Procedure này trả về ResultSet chứa thông tin top buyers
     */
    public void getTopBuyers() throws SQLException {
        // Cú pháp gọi Stored Procedure trong JDBC: {CALL procedure_name(params)}
        String sql = "{CALL GetTopBuyers()}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            System.out.println("\n========== TOP 5 KHÁCH HÀNG MUA NHIỀU NHẤT ==========");
            System.out.println("ID  | Tên                  | Email                    | Số đơn | Tổng chi tiêu");
            System.out.println("---------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                int totalOrders = rs.getInt("total_orders");
                double totalSpent = rs.getDouble("total_spent");

                System.out.printf("%-3d | %-20s | %-24s | %-6d | %s%n",
                        id, name, email, totalOrders, currencyFormat.format(totalSpent));
            }
            System.out.println("=====================================================================\n");
        }
    }

    /**
     * TÍNH DOANH THU THEO DANH MỤC (SỬ DỤNG CALLABLE STATEMENT VỚI OUT PARAMETER)
     *
     * Gọi Stored Procedure: CalculateCategoryRevenue(IN categoryName, OUT totalRevenue)
     * Procedure này trả về doanh thu qua OUT parameter
     *
     * @param category - tên danh mục cần tính doanh thu
     * @return tổng doanh thu của danh mục
     */
    public double getCategoryRevenue(String category) throws SQLException {
        // Cú pháp: {CALL procedure_name(?, ?)}
        // Dấu ? đầu tiên: IN parameter
        // Dấu ? thứ hai: OUT parameter
        String sql = "{CALL CalculateCategoryRevenue(?, ?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            // Set IN parameter (index 1)
            cs.setString(1, category);

            // Đăng ký OUT parameter (index 2, kiểu DECIMAL)
            cs.registerOutParameter(2, Types.DECIMAL);

            // Execute procedure
            cs.execute();

            // Lấy giá trị OUT parameter
            return cs.getDouble(2);
        }
    }

    /**
     * HIỂN THỊ DOANH THU TẤT CẢ CÁC DANH MỤC
     *
     * Gọi Stored Procedure: GetAllCategoryRevenue()
     */
    public void displayAllCategoryRevenue() throws SQLException {
        String sql = "{CALL GetAllCategoryRevenue()}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement cs = conn.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            System.out.println("\n========== DOANH THU THEO DANH MỤC ==========");
            System.out.println("Danh mục      | Số đơn | Số lượng bán | Doanh thu");
            System.out.println("--------------------------------------------------------");

            while (rs.next()) {
                String category = rs.getString("category");
                int totalOrders = rs.getInt("total_orders");
                int totalQuantity = rs.getInt("total_quantity_sold");
                double revenue = rs.getDouble("total_revenue");

                System.out.printf("%-13s | %-6d | %-12d | %s%n",
                        category, totalOrders, totalQuantity, currencyFormat.format(revenue));
            }
            System.out.println("=====================================================\n");
        }
    }

    /**
     * HIỂN THI THỐNG KÊ TỒN KHO CÁC SẢN PHẨM
     * (Không dùng Stored Procedure, dùng PreparedStatement bình thường)
     */
    public void displayStockStatus() throws SQLException {
        String sql = "SELECT id, name, category, stock, price FROM products ORDER BY stock ASC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));

            System.out.println("\n========== TÌNH TRẠNG TỒN KHO ==========");
            System.out.println("ID  | Tên sản phẩm                  | Danh mục      | Tồn kho | Giá");
            System.out.println("-----------------------------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String category = rs.getString("category");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");

                String stockStatus = stock == 0 ? "HẾT HÀNG" : String.valueOf(stock);

                System.out.printf("%-3d | %-29s | %-13s | %-7s | %s%n",
                        id, name, category, stockStatus, currencyFormat.format(price));
            }
            System.out.println("===================================================================================\n");
        }
    }
}
