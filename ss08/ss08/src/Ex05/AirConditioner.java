package Ex05;

public class AirConditioner implements Observer {

    private int temperature = 25;

    public void setTemperature(int temp) {
        temperature = temp;
        System.out.println("Dieu hoa: Nhiet do = " + temp);
    }

    @Override
    public void update(int roomTemp) {

        if (roomTemp > 30) {
            System.out.println("Dieu hoa: Nhiet do phong cao, van giu " + temperature);
        }

    }

    public int getStatus() {
        return temperature;
    }

}