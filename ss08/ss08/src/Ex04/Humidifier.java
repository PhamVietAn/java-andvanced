package Ex04;

public class Humidifier implements Observer {

    @Override
    public void update(int temperature) {

        System.out.println("May tao am: Dieu chinh do am cho nhiet do " + temperature);

    }

}