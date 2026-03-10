package id.ac.ui.cs.advprog.eshop.model.strategy;

import java.util.Map;

public class VoucherPaymentStrategy implements PaymentStrategy {
    private static final String KEY_VOUCHER_CODE = "voucherCode";
    private static final String VOUCHER_CODE_PREFIX = "ESHOP";
    private static final int VOUCHER_CODE_LENGTH = 16;
    private static final int VOUCHER_CODE_DIGIT_COUNT = 8;

    @Override
    public boolean isValid(Map<String, String> paymentData) {
        String voucherCode = paymentData.get(KEY_VOUCHER_CODE);
        if (voucherCode == null || voucherCode.length() != VOUCHER_CODE_LENGTH) {
            return false;
        }

        if (!voucherCode.startsWith(VOUCHER_CODE_PREFIX)) {
            return false;
        }

        return countDigits(voucherCode) == VOUCHER_CODE_DIGIT_COUNT;
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
}