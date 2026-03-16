package Ex05;

public class PercentageDiscount implements DiscountStrategy {
    private double percent;

    public PercentageDiscount(double percent) {
        this.percent = percent;
    }

    public double apply(double total) {
        return total - total * percent / 100;
    }

    public String getName() {
        return percent + "%";
    }
}