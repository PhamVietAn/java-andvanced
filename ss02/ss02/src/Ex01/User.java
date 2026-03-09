package Ex01;

public class User {
    private String username;
    private String role;
    private int age;

    public User() {
        this.username = "default_user";
        this.role = "USER";
        this.age = 18;
    }

    public User(String username, String role, int age) {
        this.username = username;
        this.role = role;
        this.age = age;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public int getAge() { return age; }

    @Override
    public String toString() {
        return "User{username='" + username + "', role='" + role + "', age=" + age + "}";
    }
}
