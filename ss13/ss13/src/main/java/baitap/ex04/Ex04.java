package baitap.ex04;

import utility.DatabaseManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Ex04 {
    public void loadDashboard() {
        String sql = "SELECT b.maBenhNhan, b.ten, d.tenDichVu " +
                "FROM BenhNhan b " +
                "LEFT JOIN DichVuSuDung d ON b.maBenhNhan = d.maBenhNhan";

        Map<Integer, String> benhNhanMap = new LinkedHashMap<>();
        Map<Integer, List<String>> dichVuMap = new LinkedHashMap<>();

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int maBN = rs.getInt("maBenhNhan");
                String tenBN = rs.getString("ten");
                String tenDV = rs.getString("tenDichVu");

                benhNhanMap.putIfAbsent(maBN, tenBN);
                dichVuMap.putIfAbsent(maBN, new ArrayList<>());

                if (tenDV != null) {
                    dichVuMap.get(maBN).add(tenDV);
                }
            }

            for (Integer maBN : benhNhanMap.keySet()) {
                System.out.println("Bệnh nhân: " + benhNhanMap.get(maBN));
                List<String> services = dichVuMap.get(maBN);
                if (services.isEmpty()) {
                    System.out.println("  -> Chưa sử dụng dịch vụ nào");
                } else {
                    for (String dv : services) {
                        System.out.println("  -> Dịch vụ: " + dv);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Ex04().loadDashboard();
    }
}

