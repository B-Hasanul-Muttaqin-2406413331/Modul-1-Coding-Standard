package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.model.strategy.CashOnDeliveryPaymentStrategy;
import id.ac.ui.cs.advprog.eshop.model.strategy.PaymentStrategy;
import id.ac.ui.cs.advprog.eshop.model.strategy.VoucherPaymentStrategy;
import lombok.Getter;

import java.util.Map;
import java.util.HashMap;

@Getter
public class Payment {

    private static final String METHOD_VOUCHER_CODE = "VOUCHER_CODE";
    private static final String METHOD_CASH_ON_DELIVERY = "CASH_ON_DELIVERY";

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    private static final Map<String, PaymentStrategy> strategies = new HashMap<>();

    static {
        strategies.put(METHOD_VOUCHER_CODE, new VoucherPaymentStrategy());
        strategies.put(METHOD_CASH_ON_DELIVERY, new CashOnDeliveryPaymentStrategy());
    }

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = calculateStatus(method, paymentData);
    }

    private String calculateStatus(String method, Map<String, String> paymentData) {
        PaymentStrategy strategy = strategies.get(method);

        if (strategy == null) {
            return STATUS_REJECTED;
        }

        return strategy.isValid(paymentData) ? STATUS_SUCCESS : STATUS_REJECTED;
    }
}