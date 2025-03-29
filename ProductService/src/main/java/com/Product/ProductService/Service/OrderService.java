package com.Product.ProductService.Service;

import com.Product.ProductService.Dto.OrderRequestDto;
import com.Product.ProductService.Model.Order;
import com.Product.ProductService.Repository.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(OrderRequestDto requestDto, Long userId) {
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
