package repository;

import utility.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DischargeRepository {

    public void processDischarge(int patientId, double amount) throws Exception {
        Connection conn = null;
        try {
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sqlInvoice = "INSERT INTO BT_Invoices (patient_id, amount, created_date) VALUES (?, ?, CURDATE())";
            try (PreparedStatement ps1 = conn.prepareStatement(sqlInvoice)) {
                ps1.setInt(1, patientId);
                ps1.setDouble(2, amount);
                ps1.executeUpdate();
            }

            String sqlPatient = "UPDATE BT_Patients SET status = 'Đã xuất viện' WHERE patient_id = ? AND status = 'Đang điều trị'";
            try (PreparedStatement ps2 = conn.prepareStatement(sqlPatient)) {
                ps2.setInt(1, patientId);
                int pRows = ps2.executeUpdate();
                if (pRows == 0) throw new Exception("Bệnh nhân không tồn tại hoặc đã xuất viện!");
            }

            String sqlBed = "UPDATE BT_Beds SET status = 'Trống', patient_id = NULL WHERE patient_id = ?";
            try (PreparedStatement ps3 = conn.prepareStatement(sqlBed)) {
                ps3.setInt(1, patientId);
                int bRows = ps3.executeUpdate();
                if (bRows == 0) throw new Exception("Không tìm thấy giường của bệnh nhân này!");
            }

            conn.commit();
            System.out.println("Hoàn tất thủ tục xuất viện cho bệnh nhân " + patientId);

        } catch (Exception e) {
            if (conn != null) conn.rollback();
            throw e;
        } finally {
            if (conn != null) conn.close();
        }
    }
}