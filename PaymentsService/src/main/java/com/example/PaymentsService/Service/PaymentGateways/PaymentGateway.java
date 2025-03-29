package com.example.PaymentsService.Service.PaymentGateways;

import com.stripe.exception.StripeException;

import java.util.Map;

public interface PaymentGateway {
    Map<String, String> generatePaymentLink(Long orderId, double amount) throws StripeException;
}
