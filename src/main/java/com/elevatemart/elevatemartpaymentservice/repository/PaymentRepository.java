package com.elevatemart.elevatemartpaymentservice.repository;

import com.elevatemart.elevatemartpaymentservice.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
