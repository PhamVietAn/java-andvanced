package Ex05;

public class SleepModeCommand implements Command {

    private Light light;
    private Fan fan;
    private AirConditioner ac;

    public SleepModeCommand(Light light, Fan fan, AirConditioner ac) {
        this.light = light;
        this.fan = fan;
        this.ac = ac;
    }

    @Override
    public void execute() {

        System.out.println("SleepMode: Tat den");
        light.turnOff();

        System.out.println("SleepMode: Dieu hoa set 28C");
        ac.setTemperature(28);

        System.out.println("SleepMode: Quat toc do thap");
        fan.setLow();

    }

}