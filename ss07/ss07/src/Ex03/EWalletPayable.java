package Ex03;

public interface EWalletPayable extends PaymentMethod {
    void processEWallet(double amount);
}
