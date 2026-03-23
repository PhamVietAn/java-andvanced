package BTTH.dao;

import BTTH.config.DBConnection;

import java.sql.*;

public class PrescriptionDAO {
    public double calculatePrescriptionTotal(int id){
        double total=0;
        try(
                Connection conn= DBConnection.openConnection();
                CallableStatement c=conn.prepareCall("{call alculatePrescriptionTotal(?,?)}")
        ){
            c.setInt(1,id);
            c.registerOutParameter(2, Types.DECIMAL);
            c.execute();
            total=c.getDouble(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
    // doanh thu theo ngay
    public double getDailyRevenue(String date) {
        double revenue = 0;
        try (Connection conn = DBConnection.openConnection();
             CallableStatement cs = conn.prepareCall("{CALL GetDailyRevenue(?, ?)}")) {
            cs.setDate(1, Date.valueOf(date));
            cs.registerOutParameter(2, Types.DECIMAL);
            cs.execute();
            revenue = cs.getDouble(2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return revenue;
    }
}
