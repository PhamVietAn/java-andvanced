package Ex01;

public class Light implements Device {

    @Override
    public void turnOn() {
        System.out.println("Den: Bat sang.");
    }

    @Override
    public void turnOff() {
        System.out.println("Den: Tat.");
    }
}
