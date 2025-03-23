package com.example.PaymentsService.Service;

import com.example.PaymentsService.Service.PaymentGateways.PaymentGateway;
import org.springframework.beans.factory.annotation.Autowired;

public class PaymentService {

    @Autowired
    private PaymentGateway paymentGateway;


    public String initiatePayment(String orderId, String email, String phoneNumber, Long amount) {
        // Order order = orderService.getOrderDetails(orderId)
        // Long amount = order.getAmount();
        // double amount = 10.10; // store number * 100
        // String orderId, String email, String phoneNumber, Long amount
//        Long amount = 1010L; // 10.00 => 1000

        return paymentGateway.generatePaymentLink(orderId, email, phoneNumber, amount);
    }

}
