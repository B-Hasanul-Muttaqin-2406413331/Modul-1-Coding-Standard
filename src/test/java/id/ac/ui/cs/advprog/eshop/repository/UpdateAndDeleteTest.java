package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UpdateAndDeleteTest {

    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    void testUpdateExistingProduct() {
        Product product = new Product();
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedData = new Product();
        updatedData.setProductId(product.getProductId());
        updatedData.setProductName("Updated Name");
        updatedData.setProductQuantity(20);

        Product result = productRepository.update(updatedData);

        assertNotNull(result);
        assertEquals("Updated Name", result.getProductName());
        assertEquals(20, result.getProductQuantity());
        assertEquals(product.getProductId(), result.getProductId());

        Product storedProduct = productRepository.findById(product.getProductId().toString());
        assertEquals("Updated Name", storedProduct.getProductName());
        assertEquals(20, storedProduct.getProductQuantity());
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product product = new Product();
        product.setProductName("Existing Product");
        productRepository.create(product);

        Product fakeProduct = new Product();
        fakeProduct.setProductId(UUID.randomUUID());
        fakeProduct.setProductName("Fake Update");
        fakeProduct.setProductQuantity(50);

        Product result = productRepository.update(fakeProduct);

        assertNull(result);

        Product original = productRepository.findById(product.getProductId().toString());
        assertEquals("Existing Product", original.getProductName());
    }

    @Test
    void testUpdateInEmptyRepository() {
        Product fakeProduct = new Product();
        fakeProduct.setProductId(UUID.randomUUID());
        fakeProduct.setProductName("Ghost Product");

        Product result = productRepository.update(fakeProduct);

        assertNull(result);
    }

    @Test
    void testDeleteExistingProduct() {
        Product product = new Product();
        product.setProductName("To Be Deleted");
        productRepository.create(product);

        String idToDelete = product.getProductId().toString();

        productRepository.delete(idToDelete);

        assertNull(productRepository.findById(idToDelete));

        Iterator<Product> iterator = productRepository.findAll();
        assertFalse(iterator.hasNext());
    }

    @Test
    void testDeleteNonExistentProduct() {
        Product product = new Product();
        product.setProductName("Safe Product");
        productRepository.create(product);

        String fakeId = UUID.randomUUID().toString();

        productRepository.delete(fakeId);

        assertNotNull(productRepository.findById(product.getProductId().toString()));

        Iterator<Product> iterator = productRepository.findAll();
        assertTrue(iterator.hasNext());
    }

    @Test
    void testDeleteFromEmptyRepository() {
        String fakeId = UUID.randomUUID().toString();

        productRepository.delete(fakeId);

        assertFalse(productRepository.findAll().hasNext());
    }

    @Test
    void testDeleteOneOfManyProducts() {
        Product p1 = new Product();
        p1.setProductName("Product 1");
        productRepository.create(p1);

        Product p2 = new Product();
        p2.setProductName("Product 2");
        productRepository.create(p2);

        productRepository.delete(p1.getProductId().toString());

        assertNull(productRepository.findById(p1.getProductId().toString()));
        assertNotNull(productRepository.findById(p2.getProductId().toString()));
    }
}