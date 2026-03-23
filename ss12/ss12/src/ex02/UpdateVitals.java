package ex02;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UpdateVitals {

    public static void updateVitals(int patientId, double temperature, int heartRate) {
        String sql = "UPDATE Vitals SET temperature = ?, heart_rate = ? WHERE p_id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán đúng kiểu dữ liệu
            ps.setDouble(1, temperature);
            ps.setInt(2, heartRate);
            ps.setInt(3, patientId);

            ps.executeUpdate();

            System.out.println("Cập nhật thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}