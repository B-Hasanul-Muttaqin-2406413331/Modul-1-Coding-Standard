package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductRepositoryTest {

    private ProductRepository repository;

    @BeforeEach
    void setUp() {
        repository = new ProductRepository();
    }

    @Test
    void createAssignsRandomIdWhenProductIdIsNull() {
        Product product = new Product();
        product.setProductName("Mouse");
        product.setProductQuantity(10);

        Product created = repository.create(product);

        assertSame(product, created);
        assertNotNull(created.getProductId());
    }

    @Test
    void createKeepsProvidedProductId() {
        UUID existingId = UUID.randomUUID();
        Product product = createProduct(existingId, "Monitor", 2);

        Product created = repository.create(product);

        assertEquals(existingId, created.getProductId());
    }

    @Test
    void findAllReturnsAllInsertedProducts() {
        Product first = repository.create(createProduct(UUID.randomUUID(), "A", 1));
        Product second = repository.create(createProduct(UUID.randomUUID(), "B", 2));

        List<Product> products = repository.findAll();

        assertEquals(2, products.size());
        assertEquals(first, products.get(0));
        assertEquals(second, products.get(1));
    }

    @Test
    void findByIdReturnsMatchingProductAndNullWhenNotFound() {
        Product product = repository.create(createProduct(UUID.randomUUID(), "Desk", 4));

        Product found = repository.findById(product.getProductId().toString());
        Product missing = repository.findById(UUID.randomUUID().toString());

        assertEquals(product, found);
        assertNull(missing);
    }

    @Test
    void updateChangesProductFieldsWhenIdExists() {
        UUID id = UUID.randomUUID();
        repository.create(createProduct(id, "Old Name", 1));
        Product updatedData = createProduct(id, "New Name", 9);

        Product updated = repository.update(updatedData);

        assertNotNull(updated);
        assertEquals("New Name", updated.getProductName());
        assertEquals(9, updated.getProductQuantity());
    }

    @Test
    void updateReturnsNullWhenProductDoesNotExist() {
        Product updated = repository.update(createProduct(UUID.randomUUID(), "Ghost", 1));

        assertNull(updated);
    }

    @Test
    void updateReturnsNullWhenProductIdIsNull() {
        Product product = new Product();
        product.setProductName("No Id");
        product.setProductQuantity(5);

        Product updated = repository.update(product);

        assertNull(updated);
    }

    @Test
    void deleteRemovesMatchingProductOnly() {
        Product first = repository.create(createProduct(UUID.randomUUID(), "Phone", 3));
        Product second = repository.create(createProduct(UUID.randomUUID(), "Tablet", 7));

        repository.delete(first.getProductId().toString());

        assertNull(repository.findById(first.getProductId().toString()));
        assertEquals(second, repository.findById(second.getProductId().toString()));
    }

    private Product createProduct(UUID id, String name, int quantity) {
        Product product = new Product();
        product.setProductId(id);
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }
}
