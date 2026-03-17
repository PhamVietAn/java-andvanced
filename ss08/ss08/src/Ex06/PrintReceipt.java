package Ex06;

public class PrintReceipt implements NotificationService {

    public void sendNotification(String message) {
        System.out.println("In hoa don: " + message);
    }

}