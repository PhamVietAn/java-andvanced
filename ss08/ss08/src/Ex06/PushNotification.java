package Ex06;

public class PushNotification implements NotificationService {

    public void sendNotification(String message) {
        System.out.println("Gui push notification: " + message);
    }

}