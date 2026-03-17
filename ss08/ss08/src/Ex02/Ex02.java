package Ex02;

import java.util.Scanner;

public class Ex02 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        OldThermometer oldThermometer = new OldThermometer();
        TemperatureSensor sensor = new ThermometerAdapter(oldThermometer);

        SmartHomeFacade smartHome = new SmartHomeFacade(sensor);

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Xem nhiet do");
            System.out.println("2. Roi nha");
            System.out.println("3. Che do ngu");
            System.out.println("4. Thoat");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    smartHome.getCurrentTemperature();
                    break;

                case 2:
                    smartHome.leaveHome();
                    break;

                case 3:
                    smartHome.sleepMode();
                    break;

                case 4:
                    System.exit(0);

            }
        }
    }
}
