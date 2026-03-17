package Ex04;

public class Fan implements Observer {

    @Override
    public void update(int temperature) {

        if (temperature < 20) {
            System.out.println("Quat: Nhiet do thap, tu dong TAT");
        }
        else if (temperature <= 25) {
            System.out.println("Quat: Nhiet do vua, chay toc do trung binh");
        }
        else {
            System.out.println("Quat: Nhiet do cao, chay toc do manh");
        }

    }

}