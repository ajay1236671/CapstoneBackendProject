package com.example.PaymentsService.Controller;

import com.example.PaymentsService.Dtos.PaymentRequestDto;
import com.example.PaymentsService.Service.PaymentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public String generatePaymentLink(@RequestBody PaymentRequestDto requestDto){

        return paymentService;
    }
}
