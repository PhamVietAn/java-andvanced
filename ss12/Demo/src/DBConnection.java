import java.sql.*;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ss12";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Kết nối DB thất bại!");
        }
    }
}