package com.Payment.PaymentService.Controller;

import com.Payment.PaymentService.Dtos.RefundResponse;
import com.Payment.PaymentService.Service.RefundService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/payments")
public class RefundController {

    private final RefundService refundService;

    public RefundController(RefundService refundService) {
        this.refundService = refundService;
    }

    @PostMapping("/refund")
    public ResponseEntity<Map<String, Object>> refundPayment(@RequestParam String transactionId) {
        RefundResponse refundResponse = refundService.processRefund(transactionId);
        return ResponseEntity.ok(Map.of("status", refundResponse.getStatus(), "message", refundResponse.getMessage()));
    }
}

