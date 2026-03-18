package Product;

public class ProductFactory {
    public static Product createProduct(String type, String id, String name, double price, double extra) {
        if (type.equalsIgnoreCase("physical")) {
            return new PhysicalProduct(id, name, price, extra);
        } else if (type.equalsIgnoreCase("digital")) {
            return new DigitalProduct(id, name, price, (int) extra);
        } else {
            throw new IllegalArgumentException("Loại sản phẩm không hợp lệ: " + type);
        }
    }
}
