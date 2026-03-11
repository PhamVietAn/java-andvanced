package BTTH;

import java.util.ArrayList;
import java.util.List;


public class BTTH {

    private List<User> users = new ArrayList<>();

    public void addUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        users.add(user);
    }

    public User findUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public boolean isValidEmail(String email) {
        return email != null && !email.isEmpty() && email.contains("@");
    }

    public List<User> getUsers() {
        return users;
    }
}
