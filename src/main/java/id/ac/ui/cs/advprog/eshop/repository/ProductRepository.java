package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;

@Repository
public class ProductRepository implements ProductRepositoryInterface {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if(product.getProductId() == null){
            product.setProductId(UUID.randomUUID());
        }
        productData.add(product);
        return product;
    }

    public List<Product> findAll() {
        return new ArrayList<>(productData);
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().toString().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public Product update(Product product) {
        if (product.getProductId() == null) {
            return null;
        }

        Product existingProduct = findById(product.getProductId().toString());
        if (existingProduct == null) {
            return null;
        }

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductQuantity(product.getProductQuantity());
        return existingProduct;
    }
    public void delete(String id) {
        productData.removeIf(product -> product.getProductId().toString().equals(id));
    }
}
