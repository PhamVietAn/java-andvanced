package Ex05;

public class MomoPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán MoMo: " + amount);
    }

    public String getName() {
        return "MoMo";
    }
}