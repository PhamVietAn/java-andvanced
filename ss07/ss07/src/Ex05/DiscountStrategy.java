package Ex05;

public interface DiscountStrategy {
    double apply(double total);
    String getName();
}