package com.elevatemart.elevatemartpaymentservice.dto;

import lombok.Data;

@Data
public class CustomerOrder {
    private Long orderId;
    private int quantities;
    private double amount;
    private String item;
    private String paymentMethod;

    private String address;
}

