package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class PaymentControllerTest {

    private MockMvc mockMvc;
    private PaymentService paymentService;
    private List<Payment> payments;

    @BeforeEach
    void setUp() throws Exception {
        paymentService = Mockito.mock(PaymentService.class);
        Object paymentController = createPaymentController();
        ReflectionTestUtils.setField(paymentController, "paymentService", paymentService);
        mockMvc = MockMvcBuilders.standaloneSetup(paymentController).build();

        payments = new ArrayList<>();
        Map<String, String> paymentDataOne = new HashMap<>();
        paymentDataOne.put("voucherCode", "ESHOP12345678ABC");
        paymentDataOne.put("orderId", "order-1");
        payments.add(new Payment("payment-1", "VOUCHER_CODE", paymentDataOne));

        Map<String, String> paymentDataTwo = new HashMap<>();
        paymentDataTwo.put("address", "Jl. Margonda Raya");
        paymentDataTwo.put("deliveryFee", "10000");
        paymentDataTwo.put("orderId", "order-2");
        payments.add(new Payment("payment-2", "CASH_ON_DELIVERY", paymentDataTwo));
    }

    @Test
    void getPaymentDetailShouldReturnPaymentDetailFormView() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetailForm"));
    }

    @Test
    void getPaymentDetailByIdShouldCallServiceAndRenderPaymentDetailView() throws Exception {
        Payment payment = payments.get(0);
        Mockito.when(paymentService.getPayment(payment.getId())).thenReturn(payment);

        mockMvc.perform(get("/payment/detail/{paymentId}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentDetail"))
                .andExpect(model().attribute("payment", payment));

        Mockito.verify(paymentService, Mockito.times(1)).getPayment(payment.getId());
    }

    @Test
    void getPaymentAdminListShouldCallServiceAndRenderPaymentAdminListView() throws Exception {
        Mockito.when(paymentService.getAllPayments()).thenReturn(payments);

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminList"))
                .andExpect(model().attribute("payments", payments));

        Mockito.verify(paymentService, Mockito.times(1)).getAllPayments();
    }

    @Test
    void getPaymentAdminDetailByIdShouldCallServiceAndRenderPaymentAdminDetailView() throws Exception {
        Payment payment = payments.get(1);
        Mockito.when(paymentService.getPayment(payment.getId())).thenReturn(payment);

        mockMvc.perform(get("/payment/admin/detail/{paymentId}", payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"))
                .andExpect(model().attribute("payment", payment));

        Mockito.verify(paymentService, Mockito.times(1)).getPayment(payment.getId());
    }

    @Test
    void postPaymentAdminSetStatusShouldCallServiceAndRenderPaymentAdminDetailView() throws Exception {
        Payment payment = payments.get(1);
        Mockito.when(paymentService.getPayment(payment.getId())).thenReturn(payment);
        Mockito.when(paymentService.setStatus(payment, "SUCCESS")).thenReturn(payment);

        mockMvc.perform(post("/payment/admin/set-status/{paymentId}", payment.getId())
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentAdminDetail"))
                .andExpect(model().attribute("payment", payment));

        Mockito.verify(paymentService, Mockito.times(1)).getPayment(payment.getId());
        Mockito.verify(paymentService, Mockito.times(1)).setStatus(payment, "SUCCESS");
    }

    private Object createPaymentController() throws Exception {
        Class<?> controllerClass =
                Class.forName("id.ac.ui.cs.advprog.eshop.controller.PaymentController");
        return controllerClass.getDeclaredConstructor().newInstance();
    }
}
