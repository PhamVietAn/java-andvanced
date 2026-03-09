package Ex01;

import java.util.function.*;

public class Main {
    public static void main(String[] args) {
        // Tạo User để test
        User adminUser = new User("admin01", "ADMIN", 30);
        User normalUser = new User("user01", "USER", 25);

        // 1. Predicate<User> - Kiểm tra User có phải Admin không
        Predicate<User> isAdmin = user -> "ADMIN".equals(user.getRole());
        System.out.println("=== 1. PREDICATE ===");
        System.out.println("adminUser là Admin? " + isAdmin.test(adminUser));   // true
        System.out.println("normalUser là Admin? " + isAdmin.test(normalUser)); // false

        // 2. Function<User, String> - Chuyển đổi User thành String username
        Function<User, String> getUsernameInfo = user -> "Username: " + user.getUsername();
        System.out.println("\n=== 2. FUNCTION ===");
        System.out.println(getUsernameInfo.apply(adminUser));  // Username: admin01
        System.out.println(getUsernameInfo.apply(normalUser)); // Username: user01

        // 3. Consumer<User> - In thông tin User ra Console
        Consumer<User> printUserInfo = user -> {
            System.out.println("Chi tiết User:");
            System.out.println("  - Username: " + user.getUsername());
            System.out.println("  - Role: " + user.getRole());
            System.out.println("  - Age: " + user.getAge());
        };
        System.out.println("\n=== 3. CONSUMER ===");
        printUserInfo.accept(adminUser);

        // 4. Supplier<User> - Khởi tạo User mới với giá trị mặc định
        Supplier<User> createDefaultUser = User::new;
        System.out.println("\n=== 4. SUPPLIER ===");
        User newUser = createDefaultUser.get();
        System.out.println("User mới được tạo: " + newUser);
    }
}
