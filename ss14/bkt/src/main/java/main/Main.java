package main;

import utility.DBConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static boolean checkAccountBalance(Connection conn, String accountId, double amount) throws SQLException {
        String query = "SELECT Balance FROM Accounts WHERE AccountId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accountId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    double balance = rs.getDouble("Balance");
                    return balance >= amount;
                }
                return false;
            }
        }
    }

    private static void transferMoney(Connection conn, String fromAccount, String toAccount, double amount) throws SQLException {
        try (CallableStatement cstmt = conn.prepareCall("{CALL sp_UpdateBalance(?, ?)}")) {

            cstmt.setString(1, fromAccount);
            cstmt.setDouble(2, -amount);
            cstmt.execute();

            cstmt.setString(1, toAccount);
            cstmt.setDouble(2, amount);
            cstmt.execute();
        }
    }

    private static void displayAccountInfo(Connection conn, String accountId1, String accountId2) throws SQLException {
        String query = "SELECT AccountId, FullName, Balance FROM Accounts WHERE AccountId IN (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, accountId1);
            pstmt.setString(2, accountId2);
            try (ResultSet rs = pstmt.executeQuery()) {
                System.out.printf("%-15s %-30s %-15s%n", "AccountId", "FullName", "Balance");
                while (rs.next()) {
                    System.out.printf("%-15s %-30s %-15.2f%n",
                            rs.getString("AccountId"),
                            rs.getString("FullName"),
                            rs.getDouble("Balance"));
                }
            }
        }
    }

    public static void main(String[] args) {
        String fromAccount = "ACC01";
        String toAccount = "ACC02";
        double transferAmount = 500.0;

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            if (!checkAccountBalance(conn, fromAccount, transferAmount)) {
                System.out.println("Lỗi: Tài khoản không tồn tại hoặc số dư không đủ!");
                return;
            }

            transferMoney(conn, fromAccount, toAccount, transferAmount);

            conn.commit();
            System.out.println("Chuyển khoản thành công!");

            displayAccountInfo(conn, fromAccount, toAccount);

        } catch (SQLException e) {
            System.out.println("Lỗi trong quá trình chuyển khoản: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Đã rollback giao dịch!");
                } catch (SQLException ex) {
                    System.out.println("Lỗi khi rollback: " + ex.getMessage());
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
                }
            }
        }
    }
}