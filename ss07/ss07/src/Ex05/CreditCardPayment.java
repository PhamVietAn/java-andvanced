package Ex05;

public class CreditCardPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Thanh toán thẻ tín dụng: " + amount);
    }

    public String getName() {
        return "Credit Card";
    }
}