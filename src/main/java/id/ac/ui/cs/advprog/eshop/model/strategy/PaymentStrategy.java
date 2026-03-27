package id.ac.ui.cs.advprog.eshop.model.strategy;

import java.util.Map;

public interface PaymentStrategy {
    boolean isValid(Map<String, String> paymentData);
}