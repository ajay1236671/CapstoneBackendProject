package com.Payment.PaymentService.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


public class PaymentResponse {
    private String transactionId;
    private String status;

    public PaymentResponse(String transactionId, String status) {
        this.transactionId = transactionId;
        this.status = status;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

