package Ex05;

public interface UserActions {
    // Default method logActivity
    default void logActivity(String activity) {
        System.out.println("[UserActions] User activity: " + activity);
    }

    // Phương thức riêng của UserActions
    default void viewContent() {
        System.out.println("User is viewing content...");
    }
}
