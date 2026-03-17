package Ex06;

public class CODPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toan khi nhan hang: " + amount);
    }

}