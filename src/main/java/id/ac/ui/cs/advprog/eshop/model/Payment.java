package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Builder
@Getter
public class Payment {
    private String id;
    private String method;
    private String status;
    private Map<String, String> paymentData;

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    public void setStatus(String status) {
        if (!status.equals("SUCCESS") &&
                !status.equals("REJECTED") &&
                !status.equals("PENDING")) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        this.status = status;
    }
}
