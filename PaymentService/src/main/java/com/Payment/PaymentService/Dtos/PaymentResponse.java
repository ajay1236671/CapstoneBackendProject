package com.Payment.PaymentService.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private String status;
}

