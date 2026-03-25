package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class khởi tạo Database từ file SQL script
 * Sử dụng Statement (không phải PreparedStatement) vì đây là script tĩnh
 */
public class DatabaseInitializer {

    /**
     * CHẠY FILE SQL SCRIPT ĐỂ KHỞI TẠO DATABASE
     *
     * Sử dụng Statement thay vì PreparedStatement vì:
     * - File SQL là script tĩnh, không có tham số động từ user
     * - Cần chạy nhiều câu lệnh SQL (CREATE TABLE, INSERT, etc.)
     * - Đây là tác vụ khởi tạo hệ thống, chỉ chạy 1 lần
     *
     * @param sqlFilePath - đường dẫn đến file schema.sql
     */
    public static void initializeDatabase(String sqlFilePath) {
        System.out.println("========================================");
        System.out.println("BẮT ĐẦU KHỞI TẠO DATABASE...");
        System.out.println("========================================");

        try {
            // Đọc nội dung file SQL
            String sqlScript = readSQLFile(sqlFilePath);

            if (sqlScript == null || sqlScript.trim().isEmpty()) {
                System.err.println("File SQL rỗng hoặc không đọc được!");
                return;
            }

            // Tách các câu lệnh SQL (delimiter: ;)
            // Lưu ý: MySQL delimiter cho procedures là DELIMITER // ... //
            String[] sqlStatements = splitSQLScript(sqlScript);

            // Kết nối database và execute
            try (Connection conn = DBConnection.getConnection();
                 Statement stmt = conn.createStatement()) {

                int successCount = 0;
                int totalStatements = sqlStatements.length;

                System.out.println("Tìm thấy " + totalStatements + " câu lệnh SQL trong file.");
                System.out.println("Đang thực thi...\n");

                for (String sql : sqlStatements) {
                    if (sql.trim().isEmpty()) {
                        continue;
                    }

                    try {
                        // Execute từng câu lệnh SQL
                        stmt.execute(sql);
                        successCount++;

                        // Log progress (mỗi 5 câu lệnh)
                        if (successCount % 5 == 0) {
                            System.out.println("Đã thực thi: " + successCount + "/" + totalStatements);
                        }
                    } catch (SQLException e) {
                        // Bỏ qua lỗi DROP TABLE IF NOT EXISTS (bảng chưa tồn tại)
                        if (!e.getMessage().contains("Unknown table") &&
                            !e.getMessage().contains("doesn't exist")) {
                            System.err.println("Lỗi khi thực thi câu lệnh SQL:");
                            System.err.println(sql.substring(0, Math.min(100, sql.length())) + "...");
                            System.err.println("Chi tiết lỗi: " + e.getMessage());
                        }
                    }
                }

                System.out.println("\n========================================");
                System.out.println("KHỞI TẠO DATABASE HOÀN TẤT!");
                System.out.println("Thành công: " + successCount + "/" + totalStatements + " câu lệnh");
                System.out.println("========================================\n");

            }

        } catch (Exception e) {
            System.err.println("LỖI NGHIÊM TRỌNG khi khởi tạo database:");
            e.printStackTrace();
        }
    }

    /**
     * Đọc file SQL và trả về nội dung dưới dạng String
     */
    private static String readSQLFile(String filePath) {
        StringBuilder content = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();

        } catch (IOException e) {
            System.err.println("Không thể đọc file SQL: " + filePath);
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Tách SQL script thành các câu lệnh riêng biệt
     * Xử lý đặc biệt cho DELIMITER trong MySQL Stored Procedures
     */
    private static String[] splitSQLScript(String script) {
        // Xử lý DELIMITER: thay đổi delimiter tạm thời cho procedures
        String processedScript = script;

        // Tìm các block DELIMITER // ... DELIMITER ;
        // Thay thế // thành ;; để tránh conflict khi split
        processedScript = processedScript.replaceAll("DELIMITER\\s+//", "");
        processedScript = processedScript.replaceAll("//\\s*DELIMITER\\s*;", ";;");
        processedScript = processedScript.replaceAll("END\\s+//", "END;;");

        // Split theo dấu ;
        String[] statements = processedScript.split(";");

        // Cleanup: loại bỏ comments và khoảng trắng thừa
        for (int i = 0; i < statements.length; i++) {
            statements[i] = statements[i]
                    .replaceAll("--[^\n]*", "")  // Loại bỏ comment --
                    .trim();

            // Khôi phục lại dấu ; cho procedures (từ ;;)
            statements[i] = statements[i].replace(";;", ";");
        }

        return statements;
    }

    /**
     * Kiểm tra xem database đã được khởi tạo chưa
     */
    public static boolean isDatabaseInitialized() {
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Thử query vào bảng users
            stmt.executeQuery("SELECT COUNT(*) FROM users");
            return true;

        } catch (SQLException e) {
            // Nếu bảng chưa tồn tại -> chưa khởi tạo
            return false;
        }
    }
}
