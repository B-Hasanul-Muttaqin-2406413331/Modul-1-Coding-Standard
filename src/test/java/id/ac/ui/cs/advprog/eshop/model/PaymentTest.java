package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentTest {

    private Map<String, String> voucherPaymentData;
    private Map<String, String> cashOnDeliveryPaymentData;
    private Map<String, String> bankTransferPaymentData;

    @BeforeEach
    void setUp() {
        voucherPaymentData = new HashMap<>();
        voucherPaymentData.put("voucherCode", "ESHOP12345678ABC");

        cashOnDeliveryPaymentData = new HashMap<>();
        cashOnDeliveryPaymentData.put("address", "Jl. Margonda Raya");
        cashOnDeliveryPaymentData.put("deliveryFee", "10000");

        bankTransferPaymentData = new HashMap<>();
        bankTransferPaymentData.put("bankName", "BCA");
        bankTransferPaymentData.put("referenceCode", "TRX-001");
    }

    @Test
    void testCreateVoucherPaymentIfVoucherCodeValid() {
        Payment payment = new Payment(
                "payment-1",
                "VOUCHER_CODE",
                voucherPaymentData
        );

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateVoucherPaymentIfVoucherCodeInvalid() {
        voucherPaymentData.put("voucherCode", "ESHOPINVALIDCODE");

        Payment payment = new Payment(
                "payment-2",
                "VOUCHER_CODE",
                voucherPaymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentIfAddressAndDeliveryFeeValid() {
        Payment payment = new Payment(
                "payment-3",
                "CASH_ON_DELIVERY",
                cashOnDeliveryPaymentData
        );

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentIfAddressEmpty() {
        cashOnDeliveryPaymentData.put("address", "");

        Payment payment = new Payment(
                "payment-4",
                "CASH_ON_DELIVERY",
                cashOnDeliveryPaymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateCashOnDeliveryPaymentIfDeliveryFeeEmpty() {
        cashOnDeliveryPaymentData.put("deliveryFee", "");

        Payment payment = new Payment(
                "payment-5",
                "CASH_ON_DELIVERY",
                cashOnDeliveryPaymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentIfBankNameAndReferenceCodeValid() {
        Payment payment = new Payment(
                "payment-6",
                "BANK_TRANSFER",
                bankTransferPaymentData
        );

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentIfBankNameEmpty() {
        bankTransferPaymentData.put("bankName", "");

        Payment payment = new Payment(
                "payment-7",
                "BANK_TRANSFER",
                bankTransferPaymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testCreateBankTransferPaymentIfReferenceCodeEmpty() {
        bankTransferPaymentData.put("referenceCode", "");

        Payment payment = new Payment(
                "payment-8",
                "BANK_TRANSFER",
                bankTransferPaymentData
        );

        assertEquals("REJECTED", payment.getStatus());
    }
}
