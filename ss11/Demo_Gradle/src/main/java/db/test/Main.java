package db.test;

import db.entity.Product;
import db.repository.ProductRepository;
import db.repository.impl.ProductRepositoryImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProductRepository repo = new ProductRepositoryImpl();

        List<Product> products = repo.getProducts();
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
