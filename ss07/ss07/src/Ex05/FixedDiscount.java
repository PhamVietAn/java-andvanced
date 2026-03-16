package Ex05;

public class FixedDiscount implements DiscountStrategy {
    private double amount;

    public FixedDiscount(double amount) {
        this.amount = amount;
    }

    public double apply(double total) {
        return total - amount;
    }

    public String getName() {
        return "Fixed " + amount;
    }
}