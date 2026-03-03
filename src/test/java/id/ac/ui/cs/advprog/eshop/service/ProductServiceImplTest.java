package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    private ProductRepository productRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        productService = new ProductServiceImpl();
        ReflectionTestUtils.setField(productService, "productRepository", productRepository);
    }

    @Test
    void createDelegatesToRepository() {
        Product product = createProduct("Laptop", 2);

        Product created = productService.create(product);

        verify(productRepository).create(product);
        assertSame(product, created);
    }

    @Test
    void findAllDelegatesListFromRepository() {
        Product first = createProduct("Book", 1);
        Product second = createProduct("Pen", 5);
        List<Product> expected = List.of(first, second);
        when(productRepository.findAll()).thenReturn(expected);

        List<Product> products = productService.findAll();

        assertEquals(expected, products);
    }

    @Test
    void findByIdDelegatesToRepository() {
        String id = UUID.randomUUID().toString();
        Product expected = createProduct("Chair", 8);
        expected.setProductId(UUID.fromString(id));
        when(productRepository.findById(id)).thenReturn(expected);

        Product actual = productService.findById(id);

        verify(productRepository).findById(id);
        assertEquals(expected, actual);
    }

    @Test
    void updateDelegatesToRepository() {
        Product input = createProduct("Speaker", 3);
        Product expected = createProduct("Speaker+", 6);
        when(productRepository.update(input)).thenReturn(expected);

        Product actual = productService.update(input);

        verify(productRepository).update(input);
        assertEquals(expected, actual);
    }

    @Test
    void deleteDelegatesToRepository() {
        String id = UUID.randomUUID().toString();

        productService.delete(id);

        verify(productRepository).delete(id);
    }

    private Product createProduct(String name, int quantity) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }
}
