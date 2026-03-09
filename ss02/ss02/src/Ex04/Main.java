package Ex04;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class Main {
    public static void main(String[] args) {
        // Tạo danh sách users
        List<User> users = new ArrayList<>();
        users.add(new User("alice", "alice@gmail.com"));
        users.add(new User("bob", "bob@gmail.com"));
        users.add(new User("charlie", "charlie@gmail.com"));

        System.out.println("=== Demo Method Reference trong Java 8 ===\n");

        // 1. (user) -> user.getUsername()
        // Chuyển sang: User::getUsername (Tham chiếu instance method của đối tượng bất kỳ thuộc kiểu cụ thể)
        System.out.println("1. Tham chiếu instance method của đối tượng bất kỳ thuộc kiểu cụ thể:");
        System.out.println("   Lambda: (user) -> user.getUsername()");
        System.out.println("   Method Reference: User::getUsername");
        
        // Dùng Lambda
        Function<User, String> getUsernameLambda = (user) -> user.getUsername();
        // Dùng Method Reference
        Function<User, String> getUsernameMethodRef = User::getUsername;
        
        System.out.println("   Kết quả với Lambda:");
        users.stream().map(getUsernameLambda).forEach(name -> System.out.println("   - " + name));
        System.out.println("   Kết quả với Method Reference:");
        users.stream().map(getUsernameMethodRef).forEach(name -> System.out.println("   - " + name));

        // 2. (s) -> System.out.println(s)
        // Chuyển sang: System.out::println (Tham chiếu instance method của một đối tượng cụ thể)
        System.out.println("\n2. Tham chiếu instance method của một đối tượng cụ thể:");
        System.out.println("   Lambda: (s) -> System.out.println(s)");
        System.out.println("   Method Reference: System.out::println");
        
        // Dùng Lambda
        Consumer<String> printLambda = (s) -> System.out.println(s);
        // Dùng Method Reference  
        Consumer<String> printMethodRef = System.out::println;
        
        System.out.println("   Kết quả với Lambda:");
        users.stream().map(User::getUsername).forEach(printLambda);
        System.out.println("   Kết quả với Method Reference:");
        users.stream().map(User::getUsername).forEach(printMethodRef);

        // 3. () -> new User()
        // Chuyển sang: User::new (Tham chiếu Constructor)
        System.out.println("\n3. Tham chiếu Constructor:");
        System.out.println("   Lambda: () -> new User()");
        System.out.println("   Method Reference: User::new");
        
        // Dùng Lambda
        Supplier<User> createUserLambda = () -> new User();
        // Dùng Method Reference
        Supplier<User> createUserMethodRef = User::new;
        
        System.out.println("   Tạo User bằng Lambda: " + createUserLambda.get());
        System.out.println("   Tạo User bằng Method Reference: " + createUserMethodRef.get());

        // Tổng hợp: Sử dụng tất cả Method Reference cùng lúc
        System.out.println("\n=== Tổng hợp: In tên tất cả users với Method Reference ===");
        users.stream()
             .map(User::getUsername)      // User::getUsername thay cho (user) -> user.getUsername()
             .forEach(System.out::println); // System.out::println thay cho (s) -> System.out.println(s)
    }
}
