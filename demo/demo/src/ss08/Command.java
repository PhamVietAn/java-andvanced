package ss08;

public class Command { // Giao lệnh
    public static void main(String[] args) {
        LightCommand on = new TurnOnLight(new Light());
        LightCommand off = new TurnOffLight(new Light());

        RemoteLight remoteLight = new RemoteLight();
        remoteLight.setLightCommand(on);
        remoteLight.pressPowerButton(); // Bật đèn
        remoteLight.pressUndoButton(); // Tắt đèn
    }
}

class Light{
    public void turnOn() {
        System.out.println("Bật đèn");
    }
    public void turnOff() {
        System.out.println("Tắt đèn");
    }
}

// lệnh
interface LightCommand{
    void execute(); // thực thi
    void undo(); // hoàn tác
}

class TurnOnLight implements  LightCommand {
    private Light light;
    public TurnOnLight(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOn();
    }

    @Override
    public void undo() {
        light.turnOff();
    }
}

class TurnOffLight implements  LightCommand {
    private Light light;
    public TurnOffLight(Light light) {
        this.light = light;
    }

    @Override
    public void execute() {
        light.turnOff();
    }

    @Override
    public void undo() {
        light.turnOn();
    }
}

class RemoteLight {
    private LightCommand lightCommand;
    public void setLightCommand(LightCommand lightCommand) {
        this.lightCommand = lightCommand;
    }

    public void pressPowerButton() {
        lightCommand.execute();
    }

    public void pressUndoButton() {
        lightCommand.undo();
    }
}
