package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        Map<String,String> data1 = new HashMap<>();
        data1.put("voucherCode","ESHOP1234ABCD5678");

        Map<String,String> data2 = new HashMap<>();
        data2.put("voucherCode","ESHOP8765DCBA4321");

        payments = new ArrayList<>();

        Payment payment1 = new Payment(
                "payment-1",
                "VOUCHER",
                "PENDING",
                data1
        );

        Payment payment2 = new Payment(
                "payment-2",
                "VOUCHER",
                "PENDING",
                data2
        );

        payments.add(payment1);
        payments.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);

        Payment result = paymentRepository.save(payment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals(payment.getId(), findResult.getId());
        assertEquals(payment.getMethod(), findResult.getMethod());
        assertEquals(payment.getStatus(), findResult.getStatus());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Payment updatedPayment = new Payment(
                payment.getId(),
                payment.getMethod(),
                "SUCCESS",
                payment.getPaymentData()
        );

        Payment result = paymentRepository.save(updatedPayment);
        Payment findResult = paymentRepository.findById(payment.getId());

        assertEquals(payment.getId(), result.getId());
        assertEquals("SUCCESS", findResult.getStatus());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("payment-1");

        assertEquals("payment-1", findResult.getId());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        Payment findResult = paymentRepository.findById("unknown");

        assertNull(findResult);
    }

    @Test
    void testFindAllPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> result = paymentRepository.findAll();

        assertEquals(2, result.size());
    }
}