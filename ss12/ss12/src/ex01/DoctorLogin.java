package ex01;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DoctorLogin {

    public static boolean login(String code, String pass) {

        String sql = "SELECT * FROM Doctors WHERE code = ? AND pass = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            // Gán tham số
            ps.setString(1, code);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();

            // Nếu có bản ghi → đăng nhập thành công
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}