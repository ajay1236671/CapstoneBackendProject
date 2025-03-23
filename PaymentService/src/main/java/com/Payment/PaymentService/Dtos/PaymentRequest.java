package com.Payment.PaymentService.Dtos;

import lombok.Data;

@Data
public class PaymentRequest {
    private String productId;
    private Double amount;
    private String paymentMethodId; // Stripe Payment Method ID from frontend

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMethodId() {
        return paymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
}


