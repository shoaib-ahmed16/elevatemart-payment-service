package com.elevatemart.elevatemartpaymentservice.controller;


import com.elevatemart.elevatemartpaymentservice.dto.CustomerOrder;
import com.elevatemart.elevatemartpaymentservice.entity.Order;
import com.elevatemart.elevatemartpaymentservice.dto.OrderEvent;
import com.elevatemart.elevatemartpaymentservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private KafkaTemplate<String,OrderEvent> kafkaTemplate;

    @Autowired
    private OrderRepository orderRepos;
    @PostMapping("/order")
    public void createorder(@RequestBody CustomerOrder customerOrder){
        Order order = new Order();
        order.setAmount(customerOrder.getAmount());
        order.setItem(customerOrder.getItem());
        order.setQuantities(customerOrder.getQuantities());
        order.setStatus("Created");
        try{
            order= orderRepos.save(order);
            customerOrder.setOrderId(order.getId());
            OrderEvent event= new OrderEvent();
            event.setOrder(customerOrder);
            event.setType("Order_Created");
            kafkaTemplate.send("new-order",event);
        }catch (Exception exc){
            order.setStatus("failed");
            orderRepos.save(order);
        }
    }
}
