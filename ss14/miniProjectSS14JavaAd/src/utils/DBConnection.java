package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class quản lý kết nối Database theo Singleton Pattern
 * Áp dụng thread-safe để đảm bảo an toàn trong môi trường đa luồng
 */
public class DBConnection {
    // Thông tin kết nối database
    private static final String URL = "jdbc:mysql://localhost:3306/flash_sale?createDatabaseIfNotExist=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Ho_Chi_Minh";
    private static final String USER = "root";
    private static final String PASSWORD = "123456789";

    // Instance duy nhất của DBConnection (Singleton)
    private static DBConnection instance;

    // Connection dùng chung (không khuyến khích trong môi trường đa luồng)
    private static Connection sharedConnection;

    /**
     * Private constructor để ngăn việc tạo instance từ bên ngoài
     */
    private DBConnection() {
    }

    /**
     * Lấy instance duy nhất của DBConnection (Thread-safe)
     * Sử dụng Double-Checked Locking Pattern
     */
    public static DBConnection getInstance() {
        if (instance == null) {
            synchronized (DBConnection.class) {
                if (instance == null) {
                    instance = new DBConnection();
                }
            }
        }
        return instance;
    }

    /**
     * LẤY CONNECTION DÙNG CHUNG
     * CHÚ Ý: Method này không an toàn trong môi trường đa luồng khi có transaction
     * Chỉ dùng cho các thao tác đơn giản, không cần transaction
     */
    public static Connection getConnection() throws SQLException {
        if (sharedConnection == null || sharedConnection.isClosed()) {
            sharedConnection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return sharedConnection;
    }

    /**
     * TẠO MỘT CONNECTION MỚI (QUAN TRỌNG CHO ĐA LUỒNG)
     * Mỗi thread nên có connection riêng để tránh xung đột transaction
     * Phải đóng connection sau khi sử dụng xong
     */
    public static Connection getNewConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Đóng connection an toàn
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                if (!conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Lỗi khi đóng connection: " + e.getMessage());
            }
        }
    }

    /**
     * Kiểm tra connection có còn hoạt động không
     */
    public static boolean isConnectionValid(Connection conn) {
        try {
            return conn != null && !conn.isClosed() && conn.isValid(2);
        } catch (SQLException e) {
            return false;
        }
    }
}
