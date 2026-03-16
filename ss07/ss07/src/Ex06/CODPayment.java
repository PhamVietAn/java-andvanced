package Ex06;

public class CODPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán tiền mặt tại quầy");
    }

    public String getName() {
        return "Cash";
    }
}