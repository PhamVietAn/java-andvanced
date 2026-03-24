package baitap.ex05;

import entity.Bed;
import repository.ReceptionRepository;
import java.util.Scanner;

public class Ex05 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ReceptionRepository repo = new ReceptionRepository();

        while (true) {
            System.out.println("\n=== HỆ THỐNG TIẾP NHẬN 1 CHẠM ===");
            System.out.println("1. Xem giường trống");
            System.out.println("2. Tiếp nhận bệnh nhân");
            System.out.println("3. Thoát");
            System.out.print("Chọn: ");
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    for (Bed b : repo.getEmptyBeds()) {
                        System.out.println("Giường: " + b.getBedCode() + " - " + b.getStatus());
                    }
                    break;
                case "2":
                    try {
                        System.out.print("Tên bệnh nhân: ");
                        String name = sc.nextLine();
                        System.out.print("Tuổi: ");
                        int age = Integer.parseInt(sc.nextLine());
                        System.out.print("Mã giường: ");
                        String bedCode = sc.nextLine();
                        System.out.print("Tiền tạm ứng: ");
                        double payment = Double.parseDouble(sc.nextLine());

                        repo.admitPatient(name, age, bedCode, payment);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Dữ liệu tuổi hoặc tiền phải là số!");
                    } catch (Exception e) {
                        System.out.println("Giao dịch thất bại: " + e.getMessage());
                    }
                    break;
                case "3":
                    return;
            }
        }
    }
}


