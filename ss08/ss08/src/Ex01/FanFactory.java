package Ex01;

public class FanFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("FanFactory: Da tao quat moi.");
        return new Fan();
    }
}