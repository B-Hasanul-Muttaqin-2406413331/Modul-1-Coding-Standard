package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.Map;

@Getter
public class Payment {

    private static final String METHOD_VOUCHER_CODE = "VOUCHER_CODE";
    private static final String METHOD_CASH_ON_DELIVERY = "CASH_ON_DELIVERY";

    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

    private static final String KEY_VOUCHER_CODE = "voucherCode";
    private static final String KEY_ADDRESS = "address";
    private static final String KEY_DELIVERY_FEE = "deliveryFee";

    private static final int VOUCHER_CODE_LENGTH = 16;
    private static final int VOUCHER_CODE_DIGIT_COUNT = 8;
    private static final String VOUCHER_CODE_PREFIX = "ESHOP";

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
        if (METHOD_VOUCHER_CODE.equals(method)) {
            return resolveStatus(validateVoucher(paymentData));
        }

        if (METHOD_CASH_ON_DELIVERY.equals(method)) {
            return resolveStatus(validateCashOnDelivery(paymentData));
        }

        return STATUS_REJECTED;
    }

    private boolean validateVoucher(Map<String, String> paymentData) {
        String voucherCode = paymentData.get(KEY_VOUCHER_CODE);
        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != VOUCHER_CODE_LENGTH) {
            return false;
        }

        if (!voucherCode.startsWith(VOUCHER_CODE_PREFIX)) {
            return false;
        }

        return countDigits(voucherCode) == VOUCHER_CODE_DIGIT_COUNT;
    }

    private boolean validateCashOnDelivery(Map<String, String> paymentData) {
        return isNotNullOrEmpty(paymentData.get(KEY_ADDRESS))
                && isNotNullOrEmpty(paymentData.get(KEY_DELIVERY_FEE));
    }

    private String resolveStatus(boolean isValid) {
        return isValid ? STATUS_SUCCESS : STATUS_REJECTED;
    }

    private int countDigits(String value) {
        int digitCount = 0;
        for (char c : value.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount += 1;
            }
        }
        return digitCount;
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.isEmpty();
    }
}
