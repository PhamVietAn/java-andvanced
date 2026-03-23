package ex03;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class SurgeryFee {

    public static void getSurgeryFee(int surgeryId) {

        String sql = "{call GET_SURGERY_FEE(?, ?)}";

        try (Connection conn = DBConnection.openConnection();
             CallableStatement cstmt = conn.prepareCall(sql)) {

            // IN parameter
            cstmt.setInt(1, surgeryId);

            // OUT parameter (QUAN TRỌNG)
            cstmt.registerOutParameter(2, Types.DECIMAL);

            // Execute
            cstmt.execute();

            // Lấy kết quả
            double cost = cstmt.getDouble(2);

            System.out.println("Chi phí phẫu thuật: " + cost);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
