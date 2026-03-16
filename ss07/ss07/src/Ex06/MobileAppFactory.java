package Ex06;

public class MobileAppFactory implements SalesChannelFactory {

    public DiscountStrategy createDiscount() {
        return new FirstPurchaseDiscount();
    }

    public PaymentMethod createPayment() {
        return new MomoPayment();
    }

    public NotificationService createNotification() {
        return new PushNotification();
    }

    public String getChannelName() {
        return "Mobile App";
    }
}