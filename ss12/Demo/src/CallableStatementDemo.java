import java.math.BigDecimal;
import java.sql.*;

public class CallableStatementDemo {
    public static void main(String[] args) throws SQLException {
        try(Connection conn = DBConnection.getConnection();
            CallableStatement call = conn.prepareCall("{call countStudentMoreThanGPA(?,?)}");
                ){
            call.setBigDecimal(1, BigDecimal.valueOf(2,9));
            call.registerOutParameter(2, Types.INTEGER);
            call.execute();
            ResultSet rs = call.getResultSet();
            if(rs.next()){
                System.out.println("Số lượng sinh viên có GPA > 2.9: " + rs.getInt(1));
            }
            // lấy tham số out và ử dụng
            int count = call.getInt(2);
            System.out.println("Số lượng sinh viên có GPA > 2.9 (out parameter): " + count);
        }
    }
}
