package com.example.PaymentsService.Service;

import com.example.PaymentsService.Service.PaymentGateways.PaymentGateway;
import com.example.PaymentsService.Service.PaymentGateways.stripePaymentGateway;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PaymentService {

    private PaymentGateway paymentGateway;


    public Map<String, String> initiatePayment(Long orderId, double amount) throws StripeException {

        paymentGateway = new stripePaymentGateway();
        return paymentGateway.generatePaymentLink(orderId, amount) ;
    }

}
