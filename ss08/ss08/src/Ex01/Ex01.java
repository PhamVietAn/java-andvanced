package Ex01;

import java.util.*;

public class Ex01 {

    static Scanner sc = new Scanner(System.in);
    static List<Device> devices = new ArrayList<>();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n1. Ket noi phan cung");
            System.out.println("2. Tao thiet bi");
            System.out.println("3. Bat thiet bi");
            System.out.println("4. Tat thiet bi");
            System.out.println("5. Thoat");

            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    HardwareConnection.getInstance();
                    break;

                case 2:
                    createDevice();
                    break;

                case 3:
                    turnOnDevice();
                    break;

                case 4:
                    turnOffDevice();
                    break;

                case 5:
                    return;
            }
        }
    }

    static void createDevice() {
        System.out.println("1. Den");
        System.out.println("2. Quat");
        System.out.println("3. Dieu hoa");

        int type = sc.nextInt();

        DeviceFactory factory = null;

        switch (type) {
            case 1:
                factory = new LightFactory();
                break;
            case 2:
                factory = new FanFactory();
                break;
            case 3:
                factory = new AirConditionerFactory();
                break;
        }

        if (factory != null) {
            Device device = factory.createDevice();
            devices.add(device);
        }
    }

    static void turnOnDevice() {

        if (devices.isEmpty()) {
            System.out.println("Chua co thiet bi.");
            return;
        }

        for (int i = 0; i < devices.size(); i++) {
            System.out.println((i + 1) + ". Thiet bi " + (i + 1));
        }

        int index = sc.nextInt() - 1;
        devices.get(index).turnOn();
    }

    static void turnOffDevice() {

        if (devices.isEmpty()) {
            System.out.println("Chua co thiet bi.");
            return;
        }

        for (int i = 0; i < devices.size(); i++) {
            System.out.println((i + 1) + ". Thiet bi " + (i + 1));
        }

        int index = sc.nextInt() - 1;
        devices.get(index).turnOff();
    }
}