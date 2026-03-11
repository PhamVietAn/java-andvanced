package Ex03;

public class Ex03 {
    public String processEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        String[] parts = email.split("@");
        if (parts.length != 2 || parts[1].isEmpty()) {
            throw new IllegalArgumentException("Invalid email domain");
        }
        return email.toLowerCase();
    }
}
