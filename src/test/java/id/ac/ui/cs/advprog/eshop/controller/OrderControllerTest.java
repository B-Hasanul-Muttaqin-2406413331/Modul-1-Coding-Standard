package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class OrderControllerTest {

    private MockMvc mockMvc;

    private OrderService orderService;
    private PaymentService paymentService;

    private List<Order> orders;

    @BeforeEach
    void setUp() throws Exception {
        orderService = Mockito.mock(OrderService.class);
        paymentService = Mockito.mock(PaymentService.class);
        Object orderController = createOrderController();
        ReflectionTestUtils.setField(orderController, "orderService", orderService);
        tryInjectField(orderController, "paymentService", paymentService);
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

    @Test
    void getOrderPayPageShouldCallServiceAndRenderOrderPayView() throws Exception {
        Order order = orders.get(0);
        Mockito.when(orderService.findById(order.getId())).thenReturn(order);

        mockMvc.perform(get("/order/pay/{orderId}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("orderPay"))
                .andExpect(model().attribute("order", order));

        Mockito.verify(orderService, Mockito.times(1)).findById(order.getId());
    }

    @Test
    void postOrderPayShouldCallServiceAndRenderPaymentResultView() throws Exception {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        paymentData.put("address", "Jl. Margonda Raya");
        paymentData.put("deliveryFee", "10000");

        Payment payment = new Payment("payment-1", "VOUCHER_CODE", paymentData);

        Mockito.when(orderService.findById(order.getId())).thenReturn(order);
        Mockito.when(paymentService.addPayment(
                Mockito.eq(order),
                Mockito.eq("VOUCHER_CODE"),
                Mockito.anyMap()
        )).thenReturn(payment);

        mockMvc.perform(post("/order/pay/{orderId}", order.getId())
                        .param("method", "VOUCHER_CODE")
                        .param("voucherCode", "ESHOP12345678ABC")
                        .param("address", "Jl. Margonda Raya")
                        .param("deliveryFee", "10000"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentResult"))
                .andExpect(model().attribute("paymentId", payment.getId()));

        Mockito.verify(orderService, Mockito.times(1)).findById(order.getId());

        ArgumentCaptor<Map<String, String>> paymentDataCaptor = ArgumentCaptor.forClass(Map.class);
        Mockito.verify(paymentService, Mockito.times(1)).addPayment(
                Mockito.eq(order),
                Mockito.eq("VOUCHER_CODE"),
                paymentDataCaptor.capture()
        );

        Map<String, String> capturedPaymentData = paymentDataCaptor.getValue();
        assertEquals("ESHOP12345678ABC", capturedPaymentData.get("voucherCode"));
        assertEquals("Jl. Margonda Raya", capturedPaymentData.get("address"));
        assertEquals("10000", capturedPaymentData.get("deliveryFee"));
    }

    private Object createOrderController() throws Exception {
        Class<?> controllerClass =
                Class.forName("id.ac.ui.cs.advprog.eshop.controller.OrderController");
        return controllerClass.getDeclaredConstructor().newInstance();
    }

    private void tryInjectField(Object target, String fieldName, Object value) {
        try {
            ReflectionTestUtils.setField(target, fieldName, value);
        } catch (IllegalArgumentException ignored) {
        }
    }
}
