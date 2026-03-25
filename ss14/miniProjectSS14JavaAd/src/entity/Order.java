package entity;

import java.sql.Timestamp;

/**
 * Entity class đại diện cho đơn hàng trong hệ thống
 */
public class Order {
    private int id;
    private int userId;
    private double totalAmount;
    private Timestamp createdAt;

    // Constructor mặc định
    public Order() {
    }

    // Constructor đầy đủ
    public Order(int id, int userId, double totalAmount, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.totalAmount = totalAmount;
        this.createdAt = createdAt;
    }

    // Constructor không có id (dùng khi insert)
    public Order(int userId) {
        this.userId = userId;
        this.totalAmount = 0;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", totalAmount=" + totalAmount +
                ", createdAt=" + createdAt +
                '}';
    }
}
