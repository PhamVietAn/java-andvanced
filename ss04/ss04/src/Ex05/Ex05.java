package Ex05;

enum Role {
    ADMIN,
    MODERATOR,
    USER
}
enum Action {
    DELETE_USER,
    LOCK_USER,
    VIEW_PROFILE
}
class User {
    private String username;
    private Role role;
    public User(String username, Role role) {
        this.username = username;
        this.role = role;
    }
    public Role getRole() {
        return role;
    }
}
public class Ex05 {
    public boolean canPerformAction(User user, Action action) {
        Role role = user.getRole();
        switch (role) {
            case ADMIN:
                return true;
            case MODERATOR:
                return action == Action.LOCK_USER || action == Action.VIEW_PROFILE;
            case USER:
                return action == Action.VIEW_PROFILE;
            default:
                return false;
        }
    }
}
