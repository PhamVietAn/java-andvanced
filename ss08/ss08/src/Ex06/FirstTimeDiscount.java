package Ex06;

public class FirstTimeDiscount implements DiscountStrategy {

    public double applyDiscount(double amount) {

        double discount = amount * 0.15;
        System.out.println("Ap dung giam gia 15% (lan dau): " + discount);

        return amount - discount;
    }

}