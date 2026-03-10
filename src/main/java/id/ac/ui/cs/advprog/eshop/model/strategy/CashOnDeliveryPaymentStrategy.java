package id.ac.ui.cs.advprog.eshop.model.strategy;

import java.util.Map;

public class CashOnDeliveryPaymentStrategy implements PaymentStrategy {
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DELIVERY_FEE = "deliveryFee";

    @Override
    public boolean isValid(Map<String, String> paymentData) {
        String address = paymentData.get(KEY_ADDRESS);
        String deliveryFee = paymentData.get(KEY_DELIVERY_FEE);

        return isNotNullOrEmpty(address) && isNotNullOrEmpty(deliveryFee);
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}