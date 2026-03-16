package Ex03;

public class PaymentProcessor {

    public void processPayment(PaymentMethod payment, double amount) {

        if (payment instanceof CODPayable) {
            ((CODPayable) payment).processCOD(amount);
        }
        else if (payment instanceof CardPayable) {
            ((CardPayable) payment).processCreditCard(amount);
        }
        else if (payment instanceof EWalletPayable) {
            ((EWalletPayable) payment).processEWallet(amount);
        }
        else {
            System.out.println("Phương thức thanh toán không hợp lệ");
        }
    }
}