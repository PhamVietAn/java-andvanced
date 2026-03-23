package ex05.DAO;

import ex05.db.DBConnection;
import ex05.model.Patient;

import java.sql.*;

public class PatientDAO {
    // hiển thị danh sách
    public void getAll() {
        String sql = "select * from patients";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getInt("age") + " | " +
                                rs.getString("department")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // thêm bệnh nhân
    public void insert(Patient p) {
        String sql = "insert into patients(name, age, department, disease, admission_date) values (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, p.getName());
            ps.setInt(2, p.getAge());
            ps.setString(3, p.getDepartment());
            ps.setString(4, p.getDisease());
            ps.setDate(5, new java.sql.Date(p.getAdmissionDate().getTime()));

            ps.executeUpdate();
            System.out.println("thêm thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // cập nhật bệnh án
    public void updateDisease(int id, String disease) {
        String sql = "update patients set disease = ? where id = ?";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, disease);
            ps.setInt(2, id);

            ps.executeUpdate();
            System.out.println("cập nhật thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // xuất viện + tính phí
    public void discharge(int id) {
        String call = "{call calculate_discharge_fee(?, ?)}";

        try (Connection conn = DBConnection.openConnection();
             CallableStatement cs = conn.prepareCall(call)) {

            cs.setInt(1, id);
            cs.registerOutParameter(2, Types.DOUBLE);

            cs.execute();

            double fee = cs.getDouble(2);
            System.out.println("viện phí: " + fee);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
