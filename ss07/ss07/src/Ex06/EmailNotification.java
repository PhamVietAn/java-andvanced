package Ex06;

public class EmailNotification implements NotificationService {

    public void send(String message) {
        System.out.println("Gửi email xác nhận");
    }
}