package Ex06;

public class MemberDiscount implements DiscountStrategy {

    public double applyDiscount(double amount) {

        double discount = amount * 0.05;
        System.out.println("Ap dung giam gia thanh vien 5%: " + discount);

        return amount - discount;
    }

}