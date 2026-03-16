package Ex06;

public class OrderService {

    public void processOrder(double total,
                             DiscountStrategy discount,
                             PaymentMethod payment,
                             NotificationService notify) {

        double finalAmount = discount.apply(total);

        payment.pay(finalAmount);

        notify.send("Order success");
    }
}