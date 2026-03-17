package Ex02;

public class AirConditioner {

    public void turnOff() {
        System.out.println("FACADE: Tat dieu hoa");
    }

    public void setTemperature(int temp) {
        System.out.println("FACADE: Dieu hoa set " + temp + "°C");
    }

}