package com.Payment.PaymentService.Controller;


import com.Payment.PaymentService.Dtos.PaymentRequest;
import com.Payment.PaymentService.Dtos.PaymentResponse;
import com.Payment.PaymentService.Models.Payment;
import com.Payment.PaymentService.Service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, Object>> processPayment(HttpServletRequest request, @RequestBody PaymentRequest paymentRequest) {
        String userId = (String) request.getAttribute("userId");

        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized user"));
        }

        PaymentResponse paymentResponse = paymentService.processPayment(userId, paymentRequest);
        return ResponseEntity.ok(Map.of("status", paymentResponse.getStatus(), "transactionId", paymentResponse.getTransactionId()));
    }
}



