package Ex06;

public class WebsiteDiscount implements DiscountStrategy {

    public double applyDiscount(double amount) {

        double discount = amount * 0.10;
        System.out.println("Ap dung giam gia 10%: " + discount);

        return amount - discount;
    }

}