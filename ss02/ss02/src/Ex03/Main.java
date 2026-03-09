package Ex03;

public class Main {
    public static void main(String[] args) {
        // Sử dụng Lambda expression để tạo instance của Authenticatable
        Authenticatable user1 = () -> "mySecretPassword";
        Authenticatable user2 = () -> "";
        Authenticatable user3 = () -> null;

        System.out.println("=== Demo Functional Interface Authenticatable ===");

        // Test isAuthenticated()
        System.out.println("\n1. Kiểm tra isAuthenticated():");
        System.out.println("User1 authenticated: " + user1.isAuthenticated()); // true
        System.out.println("User2 authenticated: " + user2.isAuthenticated()); // false
        System.out.println("User3 authenticated: " + user3.isAuthenticated()); // false

        // Test getPassword()
        System.out.println("\n2. Lấy mật khẩu với getPassword():");
        System.out.println("User1 password: " + user1.getPassword());

        // Test static method encrypt()
        System.out.println("\n3. Mã hóa mật khẩu với encrypt():");
        String rawPassword = "password123";
        String encryptedPassword = Authenticatable.encrypt(rawPassword);
        System.out.println("Raw password: " + rawPassword);
        System.out.println("Encrypted password: " + encryptedPassword);
    }
}
