package Ex06;

public interface SalesChannelFactory {

    DiscountStrategy createDiscountStrategy();

    PaymentMethod createPaymentMethod();

    NotificationService createNotificationService();

}