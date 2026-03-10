package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final String KEY_ORDER_ID = "orderId";
    private static final String PAYMENT_STATUS_SUCCESS = "SUCCESS";
    private static final String PAYMENT_STATUS_REJECTED = "REJECTED";

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderService orderService;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(
                UUID.randomUUID().toString(),
                method,
                createPaymentData(order, paymentData)
        );
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        String orderId = payment.getPaymentData().get(KEY_ORDER_ID);
        updateOrderStatus(orderId, status);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.getAllPayments();
    }

    private Map<String, String> createPaymentData(Order order, Map<String, String> paymentData) {
        Map<String, String> savedPaymentData = new HashMap<>(paymentData);
        savedPaymentData.put(KEY_ORDER_ID, order.getId());
        return savedPaymentData;
    }

    private void updateOrderStatus(String orderId, String paymentStatus) {
        if (PAYMENT_STATUS_SUCCESS.equals(paymentStatus)) {
            orderService.updateStatus(orderId, OrderStatus.SUCCESS.getValue());
        } else if (PAYMENT_STATUS_REJECTED.equals(paymentStatus)) {
            orderService.updateStatus(orderId, OrderStatus.FAILED.getValue());
        }
    }
}
