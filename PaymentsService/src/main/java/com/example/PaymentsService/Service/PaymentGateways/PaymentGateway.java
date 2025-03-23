package com.example.PaymentsService.Service.PaymentGateways;

import com.stripe.exception.StripeException;

public interface PaymentGateway {
    String generatePaymentLink(String orderId, Long amount) throws StripeException;
}
