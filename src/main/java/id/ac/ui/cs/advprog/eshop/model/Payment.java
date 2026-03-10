package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {

    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        this.status = calculateStatus(method, paymentData);
    }

    private String calculateStatus(String method, Map<String, String> paymentData) {
        if ("VOUCHER_CODE".equals(method)) {
            return validateVoucher(paymentData) ? "SUCCESS" : "REJECTED";
        }

        if ("CASH_ON_DELIVERY".equals(method)) {
            return validateCashOnDelivery(paymentData) ? "SUCCESS" : "REJECTED";
        }

        if ("BANK_TRANSFER".equals(method)) {
            return validateBankTransfer(paymentData) ? "SUCCESS" : "REJECTED";
        }

        return "REJECTED";
    }

    private boolean validateVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount += 1;
            }
        }
        return digitCount == 8;
    }

    private boolean validateCashOnDelivery(Map<String, String> paymentData) {
        return isNotNullOrEmpty(paymentData.get("address"))
                && isNotNullOrEmpty(paymentData.get("deliveryFee"));
    }

    private boolean validateBankTransfer(Map<String, String> paymentData) {
        return isNotNullOrEmpty(paymentData.get("bankName"))
                && isNotNullOrEmpty(paymentData.get("referenceCode"));
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
