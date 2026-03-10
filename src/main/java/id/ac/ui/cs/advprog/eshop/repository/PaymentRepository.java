package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {

    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        int paymentIndex = findIndexById(payment.getId());
        if (paymentIndex != -1) {
            paymentData.set(paymentIndex, payment);
            return payment;
        }

        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        int paymentIndex = findIndexById(paymentId);
        if (paymentIndex != -1) {
            return paymentData.get(paymentIndex);
        }

        return null;
    }

    public List<Payment> getAllPayments() {
        return paymentData;
    }

    private int findIndexById(String paymentId) {
        int i = 0;
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getId().equals(paymentId)) {
                return i;
            }
            i += 1;
        }

        return -1;
    }
}
