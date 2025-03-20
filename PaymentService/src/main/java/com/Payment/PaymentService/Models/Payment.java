package com.Payment.PaymentService.Models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "payments")
@Data
@NoArgsConstructor
@AllArgsConstructor
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

}

