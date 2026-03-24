package baitap.BTTH;

import repository.DischargeRepository;

public class BTTH {
    public static void main(String[] args) {
        DischargeRepository repo = new DischargeRepository();

        System.out.println("--- Test Case 1: Xuất viện thành công ---");
        try {
            repo.processDischarge(101, 2000000);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }

        System.out.println("\n--- Test Case 2: Rollback do sai bệnh nhân ---");
        try {
            repo.processDischarge(999, 1500000);
        } catch (Exception e) {
            System.out.println("Lỗi: " + e.getMessage());
        }
    }
}
