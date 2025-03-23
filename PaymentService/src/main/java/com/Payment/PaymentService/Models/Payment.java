package com.Payment.PaymentService.Models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private String transactionId;
    private Double amount;
    private String currency;
    private String status;
    private LocalDateTime timestamp;

    public Payment() {

    }

    public Payment(Long id, String userId, String transactionId, Double amount, String currency, String status, LocalDateTime timestamp) {
        this.id = id;
        this.userId = userId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.timestamp = timestamp;
    }
}

