package com.Payment.PaymentService.Dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String productId;
    private Double amount;
    private String paymentMethodId; // Stripe Payment Method ID from frontend
}


