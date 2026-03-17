package Ex01;

public class AirConditioner implements Device {

    @Override
    public void turnOn() {
        System.out.println("Dieu hoa: Bat lam mat.");
    }

    @Override
    public void turnOff() {
        System.out.println("Dieu hoa: Tat.");
    }
}