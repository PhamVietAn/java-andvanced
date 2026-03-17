package Ex01;

public class LightFactory extends DeviceFactory {

    @Override
    public Device createDevice() {
        System.out.println("LightFactory: Da tao den moi.");
        return new Light();
    }
}