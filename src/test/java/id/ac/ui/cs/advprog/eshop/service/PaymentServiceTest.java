package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    PaymentService paymentService;
    PaymentRepository paymentRepository;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentService = new PaymentService(paymentRepository);
    }

    @Test
    void testCreatePayment() {
        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP12345678ABCD");

        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                data
        );

        Payment result = paymentService.createPayment(payment);

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getMethod(), result.getMethod());
        assertEquals(payment.getStatus(), result.getStatus());
    }

    @Test
    void testFindPaymentByIdIfExists() {
        Map<String, String> data = new HashMap<>();

        Payment payment = new Payment(
                "payment-1",
                "COD",
                "PENDING",
                data
        );

        paymentService.createPayment(payment);

        Payment result = paymentService.getPaymentById("payment-1");

        assertEquals("payment-1", result.getId());
    }

    @Test
    void testFindPaymentByIdIfNotExists() {
        Payment result = paymentService.getPaymentById("unknown");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        Map<String, String> data = new HashMap<>();

        Payment payment1 = new Payment("1","COD","PENDING",data);
        Payment payment2 = new Payment("2","COD","PENDING",data);

        paymentService.createPayment(payment1);
        paymentService.createPayment(payment2);

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }
}