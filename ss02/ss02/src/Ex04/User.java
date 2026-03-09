package Ex04;

public class User {
    private String username;
    private String email;

    public User() {
        this.username = "default_user";
        this.email = "default@email.com";
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "User{username='" + username + "', email='" + email + "'}";
    }
}
