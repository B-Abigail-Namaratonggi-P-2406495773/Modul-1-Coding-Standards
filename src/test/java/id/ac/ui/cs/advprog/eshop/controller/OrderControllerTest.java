package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private ProductService productService;

    @Test
    public void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"));
    }

    @Test
    public void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("historyOrder"));
    }

    @Test
    public void testOrderHistoryPost() throws Exception {
        mockMvc.perform(post("/order/history")
                        .param("author", "Abigail"))
                .andExpect(status().isOk())
                .andExpect(view().name("listOrder"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    public void testOrderPayPage() throws Exception {
        String orderId = "123-abc";
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("p1");
        products.add(product);

        Order order = new Order(orderId, products, System.currentTimeMillis(), "Abigail");

        org.mockito.Mockito.when(orderService.findById(orderId)).thenReturn(order);

        mockMvc.perform(get("/order/pay/" + orderId))
                .andExpect(status().isOk())
                .andExpect(view().name("payOrder"));
    }

    @Test
    public void testOrderPayPost() throws Exception {
        String orderId = "123-abc";

        mockMvc.perform(post("/order/pay/" + orderId)
                        .param("paymentMethod", "CASH"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentResult"))
                .andExpect(model().attributeExists("paymentId"));
    }
}