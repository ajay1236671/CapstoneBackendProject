package com.Payment.PaymentService.Service;

import com.Payment.PaymentService.Dtos.RefundResponse;
import com.Payment.PaymentService.Models.Payment;
//import com.Payment.PaymentService.Models.Refund;
import com.stripe.model.Refund;
import com.Payment.PaymentService.Repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.RefundCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefundService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    private final PaymentRepository paymentRepository;

    public RefundService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public RefundResponse processRefund(String transactionId) {
        Stripe.apiKey = stripeSecretKey;

        Optional<Payment> paymentOptional = paymentRepository.findByTransactionId(transactionId);

        if (paymentOptional.isEmpty()) {
            return new RefundResponse("FAILED", "Transaction not found.");
        }

        Payment payment = paymentOptional.get();

        try {
            RefundCreateParams params = RefundCreateParams.builder()
                    .setPaymentIntent(transactionId)
                    .build();

            Refund refund = Refund.create(params);

            return new RefundResponse(refund.getStatus(), "Refund successful");
        } catch (StripeException e) {
            return new RefundResponse("FAILED", "Error: " + e.getMessage());
        }
    }
}

