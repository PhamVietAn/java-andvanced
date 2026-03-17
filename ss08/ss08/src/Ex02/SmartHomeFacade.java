package Ex02;

public class SmartHomeFacade {

    private Light light;
    private Fan fan;
    private AirConditioner airConditioner;
    private TemperatureSensor sensor;

    public SmartHomeFacade(TemperatureSensor sensor) {

        this.light = new Light();
        this.fan = new Fan();
        this.airConditioner = new AirConditioner();
        this.sensor = sensor;
    }

    public void leaveHome() {

        light.turnOff();
        fan.turnOff();
        airConditioner.turnOff();

    }

    public void sleepMode() {

        light.turnOff();
        airConditioner.setTemperature(28);
        fan.lowSpeed();

    }

    public void getCurrentTemperature() {

        double temp = sensor.getTemperatureCelsius();

        System.out.printf("Nhiet do hien tai: %.1f°C\n", temp);
    }

}