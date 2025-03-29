package com.Product.ProductService.Client;

import com.Product.ProductService.Model.Order;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class PaymentClient {

    private final RestTemplateBuilder templateBuilder;

    public PaymentClient(RestTemplateBuilder templateBuilder) {
        this.templateBuilder = templateBuilder;
    }

    public ResponseEntity<Map> getPaymentLink(HttpServletRequest request, Order order) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", request.getHeader("Authorization"));

        // Wrapping the request with headers
        HttpEntity<Order> requestEntity = new HttpEntity<>(order, headers);

        RestTemplate restTemplate = templateBuilder.build();
        ResponseEntity<Map> response = restTemplate.exchange(
                System.getenv("PAYMENT_URL") + "/payments/createPayment",
                HttpMethod.POST,
                requestEntity,
                Map.class
        );

        return response;
    }
}
