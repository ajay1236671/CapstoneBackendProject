package com.Payment.PaymentService.Dtos;

import lombok.Data;
import lombok.NoArgsConstructor;


import lombok.*;


@Builder
public class RefundResponse {
    private String refundId;
    private String paymentId;
    private double amount;
    private String status;

    private String message;

    public RefundResponse() {
    }

    public RefundResponse(String refundId, String paymentId, double amount, String status, String message) {
        this.refundId = refundId;
        this.paymentId = paymentId;
        this.amount = amount;
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public RefundResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRefundId() {
        return refundId;
    }

    public void setRefundId(String refundId) {
        this.refundId = refundId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

