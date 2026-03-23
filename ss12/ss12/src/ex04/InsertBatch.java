package ex04;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class InsertBatch {

    public static void insertResults(List<TestResult> list) {

        String sql = "INSERT INTO Results(data) VALUES(?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            for (TestResult tr : list) {
                ps.setString(1, tr.getData());
                ps.executeUpdate();
            }

            System.out.println("Insert thành công!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}