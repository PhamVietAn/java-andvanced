import java.util.List;
import java.util.Comparator;

public class Ex05 {

    public record User(String username, String email, String status) {}

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alexander", "a@gmail.com", "ACTIVE"),
                new User("bob", "b@gmail.com", "ACTIVE"),
                new User("charlotte", "c@gmail.com", "ACTIVE"),
                new User("Benjamin", "d@gmail.com", "ACTIVE"),
                new User("anna", "e@gmail.com", "ACTIVE")
        );

        users.stream()
                .sorted(Comparator.comparingInt((User u) -> u.username().length()).reversed())
                .limit(3)
                .forEach(u -> System.out.println(u.username()));
    }
}