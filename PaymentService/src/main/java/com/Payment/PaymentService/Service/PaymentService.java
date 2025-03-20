package com.Payment.PaymentService.Service;


import com.Payment.PaymentService.Dtos.*;
import com.Payment.PaymentService.Models.Payment;
import com.Payment.PaymentService.Repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public PaymentResponse processPayment(String userId, PaymentRequest paymentRequest) {
        Stripe.apiKey = stripeSecretKey;

        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount((long) (paymentRequest.getAmount() * 100))
                    .setCurrency("inr")
                    .setPaymentMethod(paymentRequest.getPaymentMethodId())
                    .setConfirm(true)
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);

            // Save transaction details
            Payment payment = new Payment(
                    null,
                    userId,
                    intent.getId(),
                    paymentRequest.getAmount(),
                    "INR",
                    intent.getStatus(),
                    LocalDateTime.now()
            );

            paymentRepository.save(payment);

            return new PaymentResponse(intent.getId(), intent.getStatus());
        } catch (StripeException e) {
            return new PaymentResponse(null, "FAILED: " + e.getMessage());
        }
    }
}




