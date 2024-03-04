package com.elevatemart.elevatemartpaymentservice.repository;

import com.elevatemart.elevatemartpaymentservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
