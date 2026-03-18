package Product;

public class DigitalProduct extends Product {
    private int size; // in MB

    public DigitalProduct(String id, String name, double price, int size) {
        super(id, name, price);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
    @Override
    public void displayInfo() {
        System.out.println("Digital Product:");
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Price: " + price + " VNĐ");
        System.out.println("File Size: " + size + " MB");
    }
}
