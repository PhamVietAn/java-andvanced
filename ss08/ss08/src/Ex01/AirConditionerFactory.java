package Ex01;

public class AirConditionerFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("AirConditionerFactory: Da tao dieu hoa moi.");
        return new AirConditioner();
    }
}