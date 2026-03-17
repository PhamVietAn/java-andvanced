package Ex03;

public class AirConditioner {

    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Dieu hoa: Nhiet do = " + temperature);
    }

    public int getTemperature() {
        return temperature;
    }

}