package Ex01;

public class Fan implements Device {

    @Override
    public void turnOn() {
        System.out.println("Quat: Dang quay.");
    }

    @Override
    public void turnOff() {
        System.out.println("Quat: Da tat.");
    }
}