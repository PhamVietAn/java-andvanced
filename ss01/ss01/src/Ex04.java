import java.io.IOException;

public class Ex04 {

    // Method C: Giả lập việc lưu file và ném IOException
    public static void saveToFile(String userData) throws IOException {
        System.out.println("[Method C - saveToFile] Đang cố gắng lưu dữ liệu: " + userData);
        // Giả lập lỗi khi ghi file
        throw new IOException("Lỗi ghi file: Không thể truy cập đường dẫn lưu trữ!");
    }

    // Method B: Gọi Method C và tiếp tục đẩy trách nhiệm xử lý lỗi lên trên
    public static void processUserData(String userName) throws IOException {
        System.out.println("[Method B - processUserData] Đang xử lý dữ liệu người dùng: " + userName);
        String userData = "User: " + userName + ", Time: " + System.currentTimeMillis();
        saveToFile(userData); // Gọi Method C
        System.out.println("[Method B] Xử lý hoàn tất!"); // Dòng này sẽ không được in nếu có exception
    }

    // Method A: Chốt chặn cuối cùng - xử lý dứt điểm lỗi
    public static void main(String[] args) {
        System.out.println("[Method A - main] Bắt đầu chương trình...");
        System.out.println("-------------------------------------------");

        try {
            processUserData("Nguyen Van A"); // Gọi Method B
            System.out.println("[Method A] Lưu thông tin thành công!"); // Không được in nếu có exception
        } catch (IOException e) {
            System.out.println("[Method A - main] Đã bắt được ngoại lệ!");
            System.out.println("Loại lỗi: " + e.getClass().getSimpleName());
            System.out.println("Thông điệp: " + e.getMessage());
        }

        System.out.println("-------------------------------------------");
        System.out.println("[Method A - main] Chương trình kết thúc bình thường (JVM không bị crash).");
    }
}
