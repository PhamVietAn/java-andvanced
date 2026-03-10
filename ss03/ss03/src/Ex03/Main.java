package Ex03;

import java.util.Optional;

public class Main {
    public static void main(String[] args) {

        UserRepository repo = new UserRepository();

        Optional<User> user = repo.findUserByUsername("alice");

        user.ifPresent(u -> System.out.println("Welcome " + u.username()));

        System.out.println(user.map(u -> "").orElse("Guest login"));
    }
}
