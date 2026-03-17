package Ex06;

public class CreditCardPayment implements PaymentMethod {

    public void pay(double amount) {
        System.out.println("Xu ly thanh toan the tin dung: " + amount);
    }

}