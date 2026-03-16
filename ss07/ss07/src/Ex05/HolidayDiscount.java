package Ex05;

public class HolidayDiscount implements DiscountStrategy {

    public double apply(double total) {
        return total * 0.85;
    }

    public String getName() {
        return "Holiday 15%";
    }
}