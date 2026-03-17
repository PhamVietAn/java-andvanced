package Ex05;

import java.util.Scanner;

public class Ex05 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        TemperatureSensor sensor = new TemperatureSensor();

        sensor.attach(fan);
        sensor.attach(ac);

        SleepModeCommand sleepMode = new SleepModeCommand(light, fan, ac);

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Kich hoat che do ngu");
            System.out.println("2. Thay doi nhiet do");
            System.out.println("3. Xem trang thai thiet bi");
            System.out.println("4. Thoat");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    sleepMode.execute();
                    break;

                case 2:
                    System.out.print("Nhap nhiet do moi: ");
                    int temp = sc.nextInt();
                    sensor.setTemperature(temp);
                    break;

                case 3:

                    System.out.println("Den: " + light.getStatus());
                    System.out.println("Quat: " + fan.getStatus());
                    System.out.println("Dieu hoa: " + ac.getStatus());

                    break;

                case 4:
                    return;
            }
        }
    }
}