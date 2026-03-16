package Ex05;

import java.util.*;

public class OrderService {

    private OrderRepository repository;
    private NotificationService notification;

    public OrderService(OrderRepository repository,
                        NotificationService notification) {
        this.repository = repository;
        this.notification = notification;
    }

    public void createOrder(Order order,
                            DiscountStrategy discount,
                            PaymentMethod payment) {

        double total = 0;

        for (OrderItem item : order.getItems()) {
            total += item.getTotal();
        }

        double finalAmount = discount.apply(total);
        order.setFinalAmount(finalAmount);

        repository.save(order);

        payment.pay(finalAmount);

        notification.send("Order created");
    }

    public List<Order> getOrders() {
        return repository.findAll();
    }
}