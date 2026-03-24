package baitap.ex03;

import utility.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ex03 {
    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sqlCheckBalance = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            try (PreparedStatement psCheck = conn.prepareStatement(sqlCheckBalance)) {
                psCheck.setInt(1, maBenhNhan);
                try (ResultSet rs = psCheck.executeQuery()) {
                    if (rs.next()) {
                        double currentBalance = rs.getDouble("balance");
                        // BẪY 1: Bẫy logic nghiệp vụ - Kiểm tra số dư trước khi trừ tiền
                        if (currentBalance < tienVienPhi) {
                            throw new SQLException("Số dư không đủ để thanh toán viện phí!");
                        }
                    } else {
                        throw new SQLException("Không tìm thấy ví của bệnh nhân!");
                    }
                }
            }

            String sqlDeduct = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            try (PreparedStatement psDeduct = conn.prepareStatement(sqlDeduct)) {
                psDeduct.setDouble(1, tienVienPhi);
                psDeduct.setInt(2, maBenhNhan);
                psDeduct.executeUpdate();
            }

            String sqlFreeBed = "UPDATE Beds SET status = 'Trống' WHERE patient_id = ?";
            try (PreparedStatement psBed = conn.prepareStatement(sqlFreeBed)) {
                psBed.setInt(1, maBenhNhan);
                int bedRows = psBed.executeUpdate();
                // BẪY 2: Bẫy dữ liệu ảo - Kiểm tra xem có thực sự giải phóng được giường nào không
                if (bedRows == 0) {
                    throw new SQLException("Lỗi: Mã bệnh nhân không tồn tại hoặc không có giường đang sử dụng!");
                }
            }

            String sqlUpdateStatus = "UPDATE Patients SET status = 'Đã xuất viện' WHERE patient_id = ?";
            try (PreparedStatement psPatient = conn.prepareStatement(sqlUpdateStatus)) {
                psPatient.setInt(1, maBenhNhan);
                int patientRows = psPatient.executeUpdate();
                if (patientRows == 0) {
                    throw new SQLException("Lỗi: Không thể cập nhật trạng thái bệnh nhân!");
                }
            }

            conn.commit();
            System.out.println("Xuất viện và thanh toán thành công cho bệnh nhân: " + maBenhNhan);

        } catch (SQLException e) {
            System.out.println("Giao dịch thất bại: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback();
                    System.out.println("Hệ thống đã Rollback để bảo toàn dữ liệu.");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Ex03 app = new Ex03();
        System.out.println("--- Test Case 1: Thành công ---");
        app.xuatVienVaThanhToan(101, 100000);

        System.out.println("\n--- Test Case 2: Bệnh nhân không tồn tại ---");
        app.xuatVienVaThanhToan(999, 50000);
    }
}

