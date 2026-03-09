package Ex05;

public class SuperAdmin implements UserActions, AdminActions {
    private String name;

    public SuperAdmin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    @Override
    public void logActivity(String activity) {
        System.out.println("[SuperAdmin - " + name + "] " + activity);
        
    }

    // Method đặc biệt để demo việc gọi logActivity từ từng interface
    public void logActivityFromBothInterfaces(String activity) {
        System.out.println("--- Gọi logActivity từ UserActions ---");
        UserActions.super.logActivity(activity);
        
        System.out.println("--- Gọi logActivity từ AdminActions ---");
        AdminActions.super.logActivity(activity);
    }
}
