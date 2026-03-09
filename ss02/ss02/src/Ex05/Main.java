package Ex05;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Demo Diamond Problem trong Java ===\n");

        // Tạo đối tượng SuperAdmin
        SuperAdmin superAdmin = new SuperAdmin("Admin001");

        // 1. Gọi logActivity - sử dụng implementation đã override trong SuperAdmin
        System.out.println("1. Gọi logActivity() của SuperAdmin:");
        superAdmin.logActivity("Đăng nhập hệ thống");
        superAdmin.logActivity("Xóa user vi phạm");

        // 2. Các method không bị xung đột vẫn hoạt động bình thường
        System.out.println("\n2. Gọi các method riêng từ mỗi interface:");
        superAdmin.viewContent();    // Từ UserActions
        superAdmin.manageUsers();    // Từ AdminActions

        // 3. Demo gọi logActivity từ từng interface cụ thể
        System.out.println("\n3. Demo gọi logActivity từ từng interface:");
        superAdmin.logActivityFromBothInterfaces("Cập nhật cấu hình hệ thống");

        // 4. Sử dụng đa hình (Polymorphism)
        System.out.println("\n4. Demo Polymorphism:");
        UserActions userView = superAdmin;
        AdminActions adminView = superAdmin;
        
        System.out.println("Gọi qua tham chiếu UserActions:");
        userView.logActivity("User action");
        
        System.out.println("Gọi qua tham chiếu AdminActions:");
        adminView.logActivity("Admin action");
        
        // Cả hai đều gọi implementation của SuperAdmin (override)
        System.out.println("\n=== Kết luận ===");
        System.out.println("Diamond Problem được giải quyết bằng cách:");
        System.out.println("1. Override lại method bị xung đột trong class con");
        System.out.println("2. Sử dụng InterfaceName.super.methodName() nếu cần gọi implementation cụ thể");
    }
}
