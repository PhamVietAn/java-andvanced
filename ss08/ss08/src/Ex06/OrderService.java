package Ex06;

public class OrderService {

    private DiscountStrategy discount;
    private PaymentMethod payment;
    private NotificationService notification;

    public OrderService(SalesChannelFactory factory) {

        discount = factory.createDiscountStrategy();
        payment = factory.createPaymentMethod();
        notification = factory.createNotificationService();
    }

    public void processOrder(double price, int quantity) {

        double total = price * quantity;

        double finalAmount = discount.applyDiscount(total);

        payment.pay(finalAmount);

        notification.sendNotification("Don hang thanh cong");

    }

}