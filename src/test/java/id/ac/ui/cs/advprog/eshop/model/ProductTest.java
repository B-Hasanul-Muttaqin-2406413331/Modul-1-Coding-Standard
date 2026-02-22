package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ProductTest {

    @Test
    void gettersAndSettersWorkAsExpected() {
        Product product = new Product();
        UUID productId = UUID.randomUUID();

        assertNull(product.getProductId());
        assertNull(product.getProductName());
        assertEquals(0, product.getProductQuantity());

        product.setProductId(productId);
        product.setProductName("Keyboard");
        product.setProductQuantity(5);

        assertEquals(productId, product.getProductId());
        assertEquals("Keyboard", product.getProductName());
        assertEquals(5, product.getProductQuantity());
    }
}
