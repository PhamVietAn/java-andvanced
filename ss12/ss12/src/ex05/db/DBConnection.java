package ex05.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ss12?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    // private constructor để tránh tạo object
    private DBConnection() {}

    public static Connection openConnection() {
        try {
            // load driver (Java 6+ có thể bỏ nhưng vẫn nên giữ cho chắc)
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("không tìm thấy mysql driver", e);
        } catch (SQLException e) {
            throw new RuntimeException("lỗi kết nối database", e);
        }
    }
}