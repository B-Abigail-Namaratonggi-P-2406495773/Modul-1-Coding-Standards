package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Map<String, String> paymentData;

    @BeforeEach
    void setUp() {
        paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP12345678");
    }

    @Test
    void testCreatePayment() {
        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        assertEquals("payment-1", payment.getId());
        assertEquals("VOUCHER", payment.getMethod());
        assertEquals("PENDING", payment.getStatus());
        assertEquals("ESHOP12345678", payment.getPaymentData().get("voucherCode"));
    }

    @Test
    void testCreatePaymentWithEmptyData() {
        Map<String, String> emptyData = new HashMap<>();

        Payment payment = new Payment(
                "payment-1",
                "COD",
                "PENDING",
                emptyData
        );

        assertTrue(payment.getPaymentData().isEmpty());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        payment.setStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        payment.setStatus("REJECTED");

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                paymentData
        );

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setStatus("MEOW");
        });
    }
}