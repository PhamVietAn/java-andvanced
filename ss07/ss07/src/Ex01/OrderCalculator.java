package Ex01;

import java.util.Map;

public class OrderCalculator {

    public double calculateTotal(Order order) {
        double total = 0;

        for (Map.Entry<Product, Integer> entry : order.getProducts().entrySet()) {
            Product p = entry.getKey();
            int quantity = entry.getValue();

            total += p.getPrice() * quantity;
        }

        return total;
    }
}
