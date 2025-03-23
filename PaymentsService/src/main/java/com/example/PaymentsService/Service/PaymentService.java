package com.example.PaymentsService.Service;

import com.example.PaymentsService.Service.PaymentGateways.PaymentGateway;
import com.example.PaymentsService.Service.PaymentGateways.stripePaymentGateway;
import com.stripe.exception.StripeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private PaymentGateway paymentGateway;


    public String initiatePayment(String orderId, String email, String phoneNumber, Long amount) throws StripeException {
        // Order order = orderService.getOrderDetails(orderId)
        // Long amount = order.getAmount();
        // double amount = 10.10; // store number * 100
        // String orderId, String email, String phoneNumber, Long amount
//        Long amount = 1010L; // 10.00 => 1000
        paymentGateway = new stripePaymentGateway();
        return paymentGateway.generatePaymentLink(orderId, amount) ;
    }

}
