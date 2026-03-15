package Ex06;

import java.util.Scanner;

public class Ex06 {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        CinemaSystem system = new CinemaSystem();

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Bắt đầu mô phỏng");
            System.out.println("2. Tạm dừng mô phỏng");
            System.out.println("3. Tiếp tục mô phỏng");
            System.out.println("5. Xem thống kê");
            System.out.println("6. Phát hiện deadlock");
            System.out.println("7. Thoát");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.print("Số phòng: ");
                    int rooms = sc.nextInt();

                    System.out.print("Số vé/phòng: ");
                    int tickets = sc.nextInt();

                    System.out.print("Số quầy: ");
                    int counters = sc.nextInt();

                    system.start(rooms, tickets, counters);
                    break;

                case 2:
                    system.stop();
                    break;

                case 5:
                    system.statistics();
                    break;

                case 6:
                    DeadlockDetector.detectDeadlock();
                    break;

                case 7:
                    System.out.println("Đang dừng hệ thống...");
                    System.exit(0);
            }
        }
    }
}
