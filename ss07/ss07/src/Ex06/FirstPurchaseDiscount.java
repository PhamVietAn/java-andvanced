package Ex06;

public class FirstPurchaseDiscount implements DiscountStrategy {

    public double apply(double total) {
        System.out.println("Áp dụng giảm giá 15% cho lần đầu");
        return total * 0.85;
    }

    public String getName() {
        return "First Purchase";
    }
}