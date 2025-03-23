package com.example.PaymentsService.Controller;

import com.stripe.model.Event;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    @GetMapping
    public String handleWebhook(HttpServletRequest request) throws IOException {
        return "Payment Successfull";
    }
}
