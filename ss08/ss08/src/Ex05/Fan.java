package Ex05;

public class Fan implements Observer {

    private String speed = "Tat";

    public void setLow() {
        speed = "Thap";
        System.out.println("Quat: Chay toc do thap");
    }

    @Override
    public void update(int temperature) {

        if (temperature > 30) {
            speed = "Manh";
            System.out.println("Quat: Nhiet do cao, chay toc do manh");
        }

    }

    public String getStatus() {
        return speed;
    }

}