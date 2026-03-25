package entity;

import java.sql.Timestamp;

/**
 * Entity class đại diện cho người dùng trong hệ thống
 */
public class User {
    private int id;
    private String name;
    private String email;
    private Timestamp createdAt;

    // Constructor mặc định
    public User() {
    }

    // Constructor đầy đủ
    public User(int id, String name, String email, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
    }

    // Constructor không có id (dùng khi insert)
    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
