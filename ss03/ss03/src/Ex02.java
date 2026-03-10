import java.util.ArrayList;
import java.util.List;

public class Ex02 {
    public record User(String username, String email, String status) {}

    public static void main(String[] args) {

        List<User> users = new ArrayList<>();

        users.add(new User("An", "An@gmail.com", "ACTIVE"));
        users.add(new User("Duong", "Duong@yahoo.com", "INACTIVE"));
        users.add(new User("Chi", "Chi@gmail.com", "ACTIVE"));

        users.stream()
                .filter(user -> user.email().endsWith("gmail.com"))
                .forEach(user -> System.out.println(user.username()));
    }
}
