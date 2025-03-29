package com.example.PaymentsService.Controller;

import com.example.PaymentsService.Dtos.PaymentRequestDto;
import com.example.PaymentsService.Service.PaymentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> generatePaymentLink(@RequestBody PaymentRequestDto request) throws StripeException {
        return ResponseEntity.ok(paymentService.initiatePayment(request.getOrderId(), request.getTotalAmount()));
    }
}
