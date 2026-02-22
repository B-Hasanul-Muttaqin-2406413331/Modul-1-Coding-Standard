package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductControllerTest {

    private ProductService productService;
    private ProductController productController;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);
        productController = new ProductController();
        ReflectionTestUtils.setField(productController, "service", productService);
    }

    @Test
    void createProductPageReturnsCreateViewAndAddsModelAttribute() {
        Model model = new ExtendedModelMap();

        String viewName = productController.createProductPage(model);

        assertEquals("createProduct", viewName);
        Object product = model.getAttribute("product");
        assertNotNull(product);
        assertEquals(Product.class, product.getClass());
    }

    @Test
    void createProductPostCallsServiceAndRedirectsToList() {
        Product product = createProduct("Mouse Pad", 2);
        Model model = new ExtendedModelMap();

        String viewName = productController.createProductPost(product, model);

        verify(productService).create(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void listProductsAddsProductsAndReturnsListView() {
        Product first = createProduct("Bottle", 3);
        Product second = createProduct("Glass", 4);
        List<Product> products = List.of(first, second);
        when(productService.findAll()).thenReturn(products);
        Model model = new ExtendedModelMap();

        String viewName = productController.listProducts(model);

        assertEquals("productList", viewName);
        assertSame(products, model.getAttribute("products"));
    }

    @Test
    void editProductPageAddsExistingProductAndReturnsEditView() {
        String id = UUID.randomUUID().toString();
        Product product = createProduct("Notebook", 12);
        when(productService.findById(id)).thenReturn(product);
        Model model = new ExtendedModelMap();

        String viewName = productController.editProductPage(id, model);

        verify(productService).findById(id);
        assertEquals("editProduct", viewName);
        assertSame(product, model.getAttribute("product"));
    }

    @Test
    void editProductPostCallsServiceAndRedirects() {
        Product product = createProduct("Backpack", 1);

        String viewName = productController.editProductPost(product);

        verify(productService).update(product);
        assertEquals("redirect:/product/list", viewName);
    }

    @Test
    void deleteProductCallsServiceAndRedirects() {
        String id = UUID.randomUUID().toString();

        String viewName = productController.deleteProduct(id);

        verify(productService).delete(id);
        assertEquals("redirect:/product/list", viewName);
    }

    private Product createProduct(String name, int quantity) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName(name);
        product.setProductQuantity(quantity);
        return product;
    }
}
