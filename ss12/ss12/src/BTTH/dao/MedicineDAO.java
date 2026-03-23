package BTTH.dao;


import BTTH.config.DBConnection;
import BTTH.model.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineDAO {
    // update
    public void updateMedicineStock(int id,int addedQuantity){
        try(
                Connection conn= DBConnection.openConnection();
                PreparedStatement pr=conn.prepareStatement("" +
                        "UPDATE medicines SET stock=stock+ ? WHERE id=?")
        ){
            pr.setInt(1,addedQuantity);
            pr.setInt(2,id);
            int rows=pr.executeUpdate();
            System.out.println("Da update "+rows +" dong");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // tim theo khoang gia
    public List<Medicine> findMedicinesByPrice(double min, double max){
        List<Medicine> list= new ArrayList<>();
        try(
                Connection conn=DBConnection.openConnection();
                PreparedStatement pr=conn.prepareStatement("" +
                        "SELECT * FROM medicines WHERE price BETWEEN ? AND ?");
        ){
            pr.setDouble(1,min);
            pr.setDouble(2,max);
            ResultSet resultSet=pr.executeQuery();

            while (resultSet.next()){
                Medicine m=new Medicine(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getInt("stock")
                );
                list.add(m);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
