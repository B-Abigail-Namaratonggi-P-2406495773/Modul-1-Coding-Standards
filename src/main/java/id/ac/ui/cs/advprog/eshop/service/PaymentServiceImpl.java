package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private  Map<String, Order> paymentOrders = new HashMap<>();

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        String id = UUID.randomUUID().toString();

        Payment payment = new Payment(id, method, "PENDING", paymentData);

        paymentOrders.put(payment.getId(), order);
        paymentRepository.save(payment);

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        payment.setStatus(status);

        Order order = paymentOrders.get(payment.getId());

        if (order != null) {
            if (status.equals("SUCCESS")) {
                order.setStatus("SUCCESS");
            } else if (status.equals("REJECTED")) {
                order.setStatus("FAILED");
            }
        }

        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}