package com.elevatemart.elevatemartpaymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderEvent {
    private  CustomerOrder order;
    private String type;
}
