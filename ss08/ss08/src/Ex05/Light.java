package Ex05;

public class Light {

    private boolean isOn = true;

    public void turnOff() {
        isOn = false;
        System.out.println("Den: Tat");
    }

    public String getStatus() {
        return isOn ? "Bat" : "Tat";
    }

}