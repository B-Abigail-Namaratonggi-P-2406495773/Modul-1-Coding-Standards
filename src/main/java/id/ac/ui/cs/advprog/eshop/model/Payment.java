package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.Set;

@Builder
@Getter
public class Payment {

    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;

    private static final Set<String> VALID_STATUS =
            Set.of("SUCCESS", "REJECTED", "PENDING");

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;
        setStatus(status);
    }

    public void setStatus(String status) {
        if (!VALID_STATUS.contains(status)) {
            throw new IllegalArgumentException("Invalid payment status");
        }
        this.status = status;
    }
}