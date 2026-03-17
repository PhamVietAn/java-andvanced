package Ex06;

public class EmailNotification implements NotificationService {

    public void sendNotification(String message) {
        System.out.println("Gui email: " + message);
    }

}