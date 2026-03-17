package Ex06;

public class MomoPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xu ly thanh toan MoMo: " + amount);
    }

}