import java.sql.*;

public class PreparedStatementDemo {
    // kế thừa statement
    // cho phép truyền tham số vào sql

    public static void main(String[] args) {
        // mở kết nối
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement pre = conn.prepareStatement("Select * from student where id = ?")
        ) {
            // truyền tham số nếu có
            pre.setInt(1,2);
            // thực thi câu lệnh
            ResultSet res = pre.executeQuery();
            // xử lý kết quả
            if(res.next()){
                System.out.println(res.getInt("id"));
                System.out.println(res.getString("name"));
                System.out.println(res.getInt("age"));
            }
            // áp dụng cho câu lệnh insert, update, delete
             PreparedStatement pre2 = conn.prepareStatement("insert into student(name, age) values(?, ?)");
             pre2.setString(1, "Nguyen Van A");
             pre2.setInt(2, 20);
             int row = pre2.executeUpdate();
             System.out.println("row inserted: " + row);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
