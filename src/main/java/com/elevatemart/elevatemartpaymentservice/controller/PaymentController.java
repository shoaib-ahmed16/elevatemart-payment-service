package com.elevatemart.elevatemartpaymentservice.controller;

import com.elevatemart.elevatemartpaymentservice.dto.CustomerOrder;
import com.elevatemart.elevatemartpaymentservice.dto.OrderEvent;
import com.elevatemart.elevatemartpaymentservice.dto.PaymentEvent;
import com.elevatemart.elevatemartpaymentservice.entity.Payment;
import com.elevatemart.elevatemartpaymentservice.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class PaymentController {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private KafkaTemplate<String,PaymentEvent> paymentEventKafkaTemplate;

    @Autowired
    private KafkaTemplate<String,OrderEvent> orderKafkaTemplate;

    @KafkaListener(topics = "new-orders",groupId = "orders-group")
    public void processPayment(String event) throws JsonProcessingException {
        System.out.println("Process  payment  event");
        OrderEvent orderEvent =new ObjectMapper().readValue(event,OrderEvent.class);
        CustomerOrder order=orderEvent.getOrder();
        Payment payment = new Payment();
        payment.setAmount(order.getAmount());
        payment.setMode(order.getPaymentMethod());
        payment.setOrderId(order.getOrderId());
        payment.setStatus("Success");
        try {
           payment= paymentRepository.save(payment);
           PaymentEvent paymentEvent= new PaymentEvent();
           paymentEvent.setOrder(order);
           paymentEvent.setType("PAYMENT CREATED");
           paymentEventKafkaTemplate.send("new-payments",paymentEvent);
        }catch (Exception exc){
            payment.setStatus("Failed");
            payment.setOrderId(order.getOrderId());
            paymentRepository.save(payment);
            OrderEvent oe= new OrderEvent();
            oe.setOrder(order);
            oe.setType("ORDER-REVERSED");
            orderKafkaTemplate.send("reverse-orders",oe);
        }
    }
}
