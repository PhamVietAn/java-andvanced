package BTTH.model;

public class Medicine {
    private int id;
    private String name;
    private double price;
    private int stock;

    public Medicine(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    @Override
    public String toString() {
        return "ID: "+ id+
                " NAME: "+ name+
                " PRICE: "+price+
                " STOCK: "+stock+
                "\n";
    }
}