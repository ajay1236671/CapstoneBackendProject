package com.Product.ProductService.Controller;

import com.Product.ProductService.Client.PaymentClient;
import com.Product.ProductService.Dto.OrderRequestDto;
import com.Product.ProductService.Model.Order;
import com.Product.ProductService.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;

    private final PaymentClient paymentClient;

    public OrderController(PaymentClient templateBuilder, OrderService orderService) {
        this.paymentClient = templateBuilder;
        this.orderService = orderService;
    }


    @GetMapping("/success")
    public ResponseEntity<String> updateOrderSuccess(@RequestParam long orderId) {
        Order order = orderService.updateOrderPaymentStatus(orderId, "SUCCESS");
        return ResponseEntity.ok("PAYMENT SUCCESSFULL");
    }


    @GetMapping("/failed")
    public ResponseEntity<String> updateOrderFail(@RequestParam long orderId) {
        Order order = orderService.updateOrderPaymentStatus(orderId, "FAILED");
        return ResponseEntity.ok("PAYMENT FAILED");
    }

    @PostMapping("/buy")
    public ResponseEntity<Map<String, Object>> processOrder(HttpServletRequest request, @RequestBody OrderRequestDto orderRequestDto) {
        Long userId = orderRequestDto.getUserId();
        if (userId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", "Unauthorized user"));
        }
        Order order = orderService.createOrder(orderRequestDto, userId);

        ResponseEntity<Map> response = paymentClient.getPaymentLink(request, order);
        return ResponseEntity.ok(response.getBody());
    }
}
