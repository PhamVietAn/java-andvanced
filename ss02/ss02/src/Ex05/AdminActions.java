package Ex05;

public interface AdminActions {
    // Default method logActivity - TRÙNG TÊN với UserActions
    default void logActivity(String activity) {
        System.out.println("[AdminActions] Admin activity: " + activity);
    }

    // Phương thức riêng của AdminActions
    default void manageUsers() {
        System.out.println("Admin is managing users...");
    }
}
