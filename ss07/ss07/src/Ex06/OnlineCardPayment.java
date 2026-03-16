package Ex06;

public class OnlineCardPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xử lý thanh toán thẻ tín dụng qua cổng thanh toán online");
    }

    public String getName() {
        return "Credit Card";
    }
}