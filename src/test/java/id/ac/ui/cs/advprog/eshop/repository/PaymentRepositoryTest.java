package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        payments = new ArrayList<>();

        Map<String, String> paymentDataOne = new HashMap<>();
        paymentDataOne.put("voucherCode", "ESHOP12345678ABC");
        payments.add(new Payment("payment-1", "VOUCHER_CODE", paymentDataOne));

        Map<String, String> paymentDataTwo = new HashMap<>();
        paymentDataTwo.put("address", "Jl. Margonda Raya");
        paymentDataTwo.put("deliveryFee", "10000");
        payments.add(new Payment("payment-2", "CASH_ON_DELIVERY", paymentDataTwo));

        Map<String, String> paymentDataThree = new HashMap<>();
        paymentDataThree.put("bankName", "BCA");
        paymentDataThree.put("referenceCode", "TRX-001");
        payments.add(new Payment("payment-3", "BANK_TRANSFER", paymentDataThree));
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(1);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getId());
        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(1);
        paymentRepository.save(payment);

        Map<String, String> invalidVoucherData = new HashMap<>();
        invalidVoucherData.put("voucherCode", "ESHOPINVALIDCODE");
        Payment newPayment = new Payment(
                payment.getId(),
                "VOUCHER_CODE",
                invalidVoucherData
        );

        Payment result = paymentRepository.save(newPayment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(newPayment.getMethod(), findResult.getMethod());
        assertEquals("REJECTED", findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(1).getId());
        assertEquals(payments.get(1).getId(), findResult.getId());
        assertEquals(payments.get(1).getMethod(), findResult.getMethod());
        assertEquals(payments.get(1).getStatus(), findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("zczc");
        assertNull(findResult);
    }

    @Test
    void testGetAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> paymentList = paymentRepository.getAllPayments();

        assertEquals(3, paymentList.size());
        assertEquals(payments.get(0).getId(), paymentList.get(0).getId());
        assertEquals(payments.get(1).getId(), paymentList.get(1).getId());
        assertEquals(payments.get(2).getId(), paymentList.get(2).getId());
    }
}
