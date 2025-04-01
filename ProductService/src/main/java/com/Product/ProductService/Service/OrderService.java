package com.Product.ProductService.Service;

import com.Product.ProductService.Dto.OrderRequestDto;
import com.Product.ProductService.Model.Order;
import com.Product.ProductService.Model.Product;
import com.Product.ProductService.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderService(OrderRepository orderRepository,ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(OrderRequestDto requestDto, Long userId) {
        Product product = productService.getProductById(requestDto.getProductId());
        if (product == null ) {
            throw new RuntimeException("Product out of stock");
        }
        Order order = new Order(userId, requestDto.getProductId(), requestDto.getQuantity(), requestDto.getTotalAmount(), "INITIATED");
        return orderRepository.save(order);
    }

    public Order updateOrderPaymentStatus(Long orderId,String paymentStatus){
        Order order = orderRepository.getByOrderId(orderId);
        order.setPaymentStatus(paymentStatus);

        orderRepository.save(order);
        return order;
    }
}
