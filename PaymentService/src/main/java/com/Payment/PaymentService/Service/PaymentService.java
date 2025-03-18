package com.Payment.PaymentService.Service;


import com.Payment.PaymentService.Models.Payment;
import com.Payment.PaymentService.Repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;

    public PaymentService(PaymentRepository paymentRepository,RestTemplate restTemplate) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
    }

    public Payment processPayment(Payment payment) {
        // Call Product Service to verify product
        String productServiceUrl = "http://localhost:8081/products/" + payment.getId();
        Boolean isProductValid = restTemplate.getForObject(productServiceUrl, Boolean.class);

        if (Boolean.TRUE.equals(isProductValid)) {
            payment.setStatus("SUCCESS");
            return paymentRepository.save(payment);
        } else {
            throw new RuntimeException("Invalid product");
        }
    }
}

