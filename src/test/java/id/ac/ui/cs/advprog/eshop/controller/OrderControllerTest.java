package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class OrderControllerTest {

    private MockMvc mockMvc;

    private OrderService orderService;

    private List<Order> orders;

    @BeforeEach
    void setUp() throws Exception {
        orderService = Mockito.mock(OrderService.class);
        Object orderController = createOrderController();
        ReflectionTestUtils.setField(orderController, "orderService", orderService);
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        orders.add(new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        ));
        orders.add(new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        ));
    }

    @Test
    void getCreateOrderPageShouldReturnCreateView() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"));
    }

    @Test
    void getOrderHistoryShouldReturnOrderHistoryFormView() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistoryForm"));
    }

    @Test
    void postOrderHistoryShouldCallServiceAndRenderOrderHistoryList() throws Exception {
        String author = "Safira Sudrajat";
        Mockito.when(orderService.findAllByAuthor(author)).thenReturn(orders);

        mockMvc.perform(post("/order/history")
                        .param("author", author))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistoryList"))
                .andExpect(model().attribute("author", author))
                .andExpect(model().attribute("orders", orders));

        Mockito.verify(orderService, Mockito.times(1)).findAllByAuthor(author);
    }

    private Object createOrderController() throws Exception {
        Class<?> controllerClass =
                Class.forName("id.ac.ui.cs.advprog.eshop.controller.OrderController");
        return controllerClass.getDeclaredConstructor().newInstance();
    }
}
