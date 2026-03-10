package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderService orderService;

    List<Order> orders;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId(UUID.fromString("eb558e9f-1c39-460e-8860-71af6af63bd6"));
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        orders = new ArrayList<>();
        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
        orders.add(order1);

        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        );
        orders.add(order2);

        payments = new ArrayList<>();
        Map<String, String> paymentDataOne = new HashMap<>();
        paymentDataOne.put("voucherCode", "ESHOP12345678ABC");
        paymentDataOne.put("orderId", order1.getId());
        payments.add(new Payment("payment-1", "VOUCHER_CODE", paymentDataOne));

        Map<String, String> paymentDataTwo = new HashMap<>();
        paymentDataTwo.put("address", "");
        paymentDataTwo.put("deliveryFee", "10000");
        paymentDataTwo.put("orderId", order2.getId());
        payments.add(new Payment("payment-2", "CASH_ON_DELIVERY", paymentDataTwo));
    }

    @Test
    void testAddPayment() {
        Order order = orders.get(0);
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678ABC");
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER_CODE", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        Order order = orders.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService)
                .updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());

        Payment result = paymentService.setStatus(payment, "SUCCESS");

        verify(orderService, times(1))
                .updateStatus(order.getId(), OrderStatus.SUCCESS.getValue());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(1);
        Order order = orders.get(1);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderService)
                .updateStatus(order.getId(), OrderStatus.FAILED.getValue());

        Payment result = paymentService.setStatus(payment, "REJECTED");

        verify(orderService, times(1))
                .updateStatus(order.getId(), OrderStatus.FAILED.getValue());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testSetStatusUnknownDoesNotUpdateOrderStatus() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.setStatus(payment, "PENDING");

        verify(orderService, never()).updateStatus(anyString(), anyString());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetPayment() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).findById(payment.getId());

        Payment result = paymentService.getPayment(payment.getId());
        assertEquals(payment.getId(), result.getId());
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).getAllPayments();

        List<Payment> result = paymentService.getAllPayments();
        assertEquals(2, result.size());
    }
}
