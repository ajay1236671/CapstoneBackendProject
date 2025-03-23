package com.example.PaymentsService.Controller;

import com.example.PaymentsService.Dtos.PaymentRequestDto;
import com.example.PaymentsService.Service.PaymentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/createPayment")
    public ResponseEntity<?> generatePaymentLink(@RequestHeader("Authorization") String authHeader, @RequestBody PaymentRequestDto request) throws StripeException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7); // Extract token
        Map<String, Object> validationResponse = validateToken(token);

        if (!Boolean.TRUE.equals(validationResponse.get("valid"))) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }

        return ResponseEntity.ok(paymentService.initiatePayment(request.getOrderId(), request.getEmail(), request.getPhoneNumber(), request.getAmount()));
    }

    private Map<String, Object> validateToken(String token) {
        RestTemplate restTemplate = new RestTemplate();
        String validationUrl = "http://localhost:8081/auth/validate"; // Security Service endpoint

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + token);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    validationUrl, HttpMethod.POST, entity, Map.class);

            return response.getBody();
        } catch (Exception e) {
            return Collections.singletonMap("valid", false);
        }
    }
}
