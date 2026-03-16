package Ex06;

public class MomoPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xử lý thanh toán MoMo tích hợp");
    }

    public String getName() {
        return "MoMo";
    }
}