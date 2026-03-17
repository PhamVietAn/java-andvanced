package Ex01;

public class HardwareConnection {

    private static HardwareConnection instance;

    private HardwareConnection() {
    }

    public static HardwareConnection getInstance() {
        if (instance == null) {
            instance = new HardwareConnection();
            System.out.println("HardwareConnection: Da ket noi phan cung.");
        }
        return instance;
    }

    public void connect() {
        // giả lập
    }

    public void disconnect() {
        System.out.println("HardwareConnection: Ngat ket noi.");
    }
}