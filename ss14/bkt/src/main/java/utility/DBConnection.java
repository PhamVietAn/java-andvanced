package utility;

import java.sql.*;

public class DBConnection {
    private static final String CONNECTION_STRING = "jdbc:mysql://localhost:3306/bkt";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(CONNECTION_STRING,USERNAME,PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}
