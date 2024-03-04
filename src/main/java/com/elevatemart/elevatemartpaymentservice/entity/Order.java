package com.elevatemart.elevatemartpaymentservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

@Entity
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private int quantities;
    private double amount;
    private String item;
    private String status;
}
