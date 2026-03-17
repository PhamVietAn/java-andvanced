package Ex03;

import java.util.Scanner;

public class Ex03 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Fan fan = new Fan();
        AirConditioner ac = new AirConditioner();

        RemoteControl remote = new RemoteControl();

        while (true) {

            System.out.println("\n===== MENU =====");
            System.out.println("1. Gan nut");
            System.out.println("2. Nhan nut");
            System.out.println("3. Undo");
            System.out.println("4. Thoat");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:

                    System.out.println("Chon nut:");
                    int slot = sc.nextInt();

                    System.out.println("1.Light On");
                    System.out.println("2.Light Off");
                    System.out.println("3.Fan On");
                    System.out.println("4.Fan Off");
                    System.out.println("5.AC Set Temp");

                    int cmd = sc.nextInt();

                    switch (cmd) {

                        case 1:
                            remote.setCommand(slot, new LightOnCommand(light));
                            break;

                        case 2:
                            remote.setCommand(slot, new LightOffCommand(light));
                            break;

                        case 3:
                            remote.setCommand(slot, new FanOnCommand(fan));
                            break;

                        case 4:
                            remote.setCommand(slot, new FanOffCommand(fan));
                            break;

                        case 5:
                            System.out.println("Nhap nhiet do:");
                            int temp = sc.nextInt();
                            remote.setCommand(slot, new ACSetTemperatureCommand(ac, temp));
                            break;
                    }

                    break;

                case 2:

                    System.out.println("Nhan nut:");
                    int press = sc.nextInt();
                    remote.pressButton(press);
                    break;

                case 3:

                    remote.undo();
                    break;

                case 4:

                    return;

            }
        }
    }
}