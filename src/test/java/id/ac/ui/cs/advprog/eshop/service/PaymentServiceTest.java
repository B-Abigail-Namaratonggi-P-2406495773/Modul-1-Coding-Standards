package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceTest {

    PaymentService paymentService;
    PaymentRepository paymentRepository;
    Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();
        paymentService = new PaymentServiceImpl(paymentRepository);
        order = createOrder();
    }

    private Order createOrder() {

        Product product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(1);

        List<Product> products = new ArrayList<>();
        products.add(product);

        return new Order(
                "order-1",
                products,
                System.currentTimeMillis(),
                "tester"
        );
    }

    @Test
    void testAddPayment() {

        Map<String,String> data = new HashMap<>();
        data.put("voucherCode","ESHOP12345678ABC");

        Payment payment = paymentService.addPayment(order,"VOUCHER",data);

        assertNotNull(payment);
        assertEquals("VOUCHER",payment.getMethod());
        assertEquals("SUCCESS",payment.getStatus());
    }

    @Test
    void testSetStatusSuccessUpdatesOrder() {

        Payment payment = paymentService.addPayment(order,"COD",new HashMap<>());

        paymentService.setStatus(payment,"SUCCESS");

        assertEquals("SUCCESS",payment.getStatus());
        assertEquals("SUCCESS",order.getStatus());
    }

    @Test
    void testSetStatusRejectedUpdatesOrder() {

        Payment payment = paymentService.addPayment(order,"COD",new HashMap<>());

        paymentService.setStatus(payment,"REJECTED");

        assertEquals("REJECTED",payment.getStatus());
        assertEquals("FAILED",order.getStatus());
    }

    @Test
    void testGetPaymentById() {

        Payment payment = paymentService.addPayment(order,"COD",new HashMap<>());

        Payment result = paymentService.getPayment(payment.getId());

        assertEquals(payment.getId(),result.getId());
    }

    @Test
    void testGetPaymentIfNotExists() {

        Payment result = paymentService.getPayment("unknown");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {

        paymentService.addPayment(order,"COD",new HashMap<>());
        paymentService.addPayment(order,"COD",new HashMap<>());

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2,result.size());
    }

    @Test
    void testAddPaymentWithValidVoucherCode() {

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("SUCCESS", payment.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherLength() {

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOP123");

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherPrefix() {

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "TOKO1234ABC5678");

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentWithInvalidVoucherDigitCount() {

        Map<String, String> data = new HashMap<>();
        data.put("voucherCode", "ESHOPABCDEFABCDEF");

        Payment payment = paymentService.addPayment(order, "VOUCHER", data);

        assertEquals("REJECTED", payment.getStatus());
    }

    @Test
    void testAddPaymentCODValid() {

        Map<String,String> data = new HashMap<>();
        data.put("address","Depok");
        data.put("deliveryFee","10000");

        Payment payment = paymentService.addPayment(order,"COD",data);

        assertNotNull(payment);
        assertEquals("COD",payment.getMethod());
        assertEquals("PENDING",payment.getStatus());
    }

    @Test
    void testAddPaymentCODEmptyAddress() {

        Map<String,String> data = new HashMap<>();
        data.put("address","");
        data.put("deliveryFee","10000");

        Payment payment = paymentService.addPayment(order,"COD",data);

        assertEquals("REJECTED",payment.getStatus());
    }

    @Test
    void testAddPaymentCODEmptyDeliveryFee() {

        Map<String,String> data = new HashMap<>();
        data.put("address","Depok");
        data.put("deliveryFee","");

        Payment payment = paymentService.addPayment(order,"COD",data);

        assertEquals("REJECTED",payment.getStatus());
    }

    @Test
    void testAddPaymentCODNullAddress() {

        Map<String,String> data = new HashMap<>();
        data.put("address",null);
        data.put("deliveryFee","10000");

        Payment payment = paymentService.addPayment(order,"COD",data);

        assertEquals("REJECTED",payment.getStatus());
    }
}