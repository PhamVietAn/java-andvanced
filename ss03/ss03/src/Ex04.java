import java.util.*;
import java.util.stream.Collectors;

public class Ex04 {

    public record User(String username, String email, String status) {}

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("alice", "alice@gmail.com", "ACTIVE"),
                new User("bob", "bob@yahoo.com", "INACTIVE"),
                new User("alice", "alice2@gmail.com", "ACTIVE"),
                new User("charlie", "charlie@gmail.com", "ACTIVE"),
                new User("bob", "bob2@yahoo.com", "ACTIVE")
        );

        Collection<User> uniqueUsers = users.stream()
                .collect(Collectors.toMap(
                        User::username,
                        user -> user,
                        (u1, u2) -> u1
                ))
                .values();

        uniqueUsers.forEach(System.out::println);
    }
}