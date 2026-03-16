package Ex05;

import java.util.*;

public class Order {
    private String id;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();
    private double finalAmount;

    public Order(String id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public void addItem(OrderItem item) {
        items.add(item);
    }

    public List<OrderItem> getItems() { return items; }
    public String getId() { return id; }
    public Customer getCustomer() { return customer; }

    public void setFinalAmount(double finalAmount) {
        this.finalAmount = finalAmount;
    }

    public double getFinalAmount() {
        return finalAmount;
    }
}