package com.elevatemart.elevatemartpaymentservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentEvent {
    private  CustomerOrder order;
    private String type;
}
