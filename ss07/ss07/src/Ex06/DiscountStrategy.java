package Ex06;

public interface DiscountStrategy {
    double apply(double total);
    String getName();
}