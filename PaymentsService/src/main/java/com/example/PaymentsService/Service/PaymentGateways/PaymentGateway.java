package com.example.PaymentsService.Service.PaymentGateways;

public interface PaymentGateway {
    String generatePaymentLink(String orderId, String email, String phoneNumber, Long amount);
}
