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
    private final Map<String, Order> paymentOrders = new HashMap<>();

    private static final String SUCCESS = "SUCCESS";
    private static final String REJECTED = "REJECTED";
    private static final String FAILED = "FAILED";

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {

        String paymentId = UUID.randomUUID().toString();

        Payment payment = new Payment(
                paymentId,
                method,
                "PENDING",
                paymentData
        );

        if ("VOUCHER".equals(method)) {

            String voucherCode = paymentData.get("voucherCode");

            if (isValidVoucher(voucherCode)) {
                payment.setStatus(SUCCESS);
            } else {
                payment.setStatus(REJECTED);
            }
        }

        paymentRepository.save(payment);
        paymentOrders.put(paymentId, order);

        return payment;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {

        payment.setStatus(status);

        updateOrderStatus(payment, status);

        paymentRepository.save(payment);

        return payment;
    }

    private void updateOrderStatus(Payment payment, String status) {

        Order order = paymentOrders.get(payment.getId());

        if (order == null) return;

        if (status.equals(SUCCESS)) {
            order.setStatus(SUCCESS);
        } else if (status.equals(REJECTED)) {
            order.setStatus(FAILED);
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private boolean isValidVoucher(String voucherCode) {

        if (voucherCode == null) return false;

        if (voucherCode.length() != 16) return false;

        if (!voucherCode.startsWith("ESHOP")) return false;

        int digitCount = 0;

        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }


}