package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId(UUID.fromString("a0f9de46-90b1-437d-a0bf-d0821dde9096"));
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());

        assertFalse(productIterator.hasNext());
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