package db.repository;

import db.entity.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> findAll();
}
