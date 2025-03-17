package com.Payment.PaymentService.Repository;


import com.Payment.PaymentService.Models.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}

