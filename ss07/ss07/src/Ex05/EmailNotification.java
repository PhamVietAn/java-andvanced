package Ex05;

public class EmailNotification implements NotificationService {

    public void send(String message) {
        System.out.println("Đã gửi email xác nhận");
    }
}