package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;

import java.util.List;

public interface ProductRepositoryInterface {
    Product create(Product product);
    List<Product> findAll();
    Product findById(String id);
    Product update(Product product);
    void delete(String id);
}
