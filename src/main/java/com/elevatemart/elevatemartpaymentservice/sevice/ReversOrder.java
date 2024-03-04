package com.elevatemart.elevatemartpaymentservice.sevice;

import com.elevatemart.elevatemartpaymentservice.entity.Order;
import com.elevatemart.elevatemartpaymentservice.dto.OrderEvent;
import com.elevatemart.elevatemartpaymentservice.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ReversOrder {

    @Autowired
    private OrderRepository orderRepos;

    @KafkaListener(topics = "reversed-order",groupId = "orders-group")
    public void reverseOrder(String event){
        System.out.println("Reverse order event :: "+event);
        try{
            OrderEvent orderEvent = new ObjectMapper().readValue(event,OrderEvent.class);
            Optional<Order> order=orderRepos.findById(orderEvent.getOrder().getOrderId());
            order.ifPresent(o->{
                o.setStatus("Failed");
                orderRepos.save(o);
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
