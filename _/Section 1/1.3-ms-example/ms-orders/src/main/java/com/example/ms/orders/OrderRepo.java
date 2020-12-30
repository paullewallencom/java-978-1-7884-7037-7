package com.example.ms.orders;

import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderRepo {

    private List<Order> allOrders = new ArrayList<>();

    @PostConstruct
    private void init() {

        Order order = new Order();
        order.setId("72c7bd3d-ad2d-4f0b-9668-f76ccfe732d8");
        order.setStatus(OrderStatus.COMPLETED);
        order.setTotalPrice(new BigDecimal("50.25"));
        order.setNumberOfItems(2);
        order.setTransactionId("59be8061-6ac5-403c-a989-aeeba266873a");

        allOrders.add(order);

        order = new Order();
        order.setId("9db4b32d-f30a-40ae-999b-ec059e850508");
        order.setStatus(OrderStatus.COMPLETED);
        order.setTotalPrice(new BigDecimal("5.10"));
        order.setNumberOfItems(1);
        order.setTransactionId("a9756bb5-e8cb-4e24-8b1f-378d4f60a0c8");

        allOrders.add(order);

        order = new Order();
        order.setId("0b1dbb71-7ba3-4067-af9c-0bcaa467685d");
        order.setStatus(OrderStatus.IN_PROGRESS);
        order.setTotalPrice(new BigDecimal("23.99"));
        order.setNumberOfItems(3);
        order.setTransactionId("b16ad377-29c2-454f-a368-36236dcd0a73");

        allOrders.add(order);

        order = new Order();
        order.setId("122c43b4-7a3e-47bc-b653-db23f79a288a");
        order.setStatus(OrderStatus.COMPLETED);
        order.setTotalPrice(new BigDecimal("74.49"));
        order.setNumberOfItems(10);
        order.setTransactionId("963f3f9e-f846-457e-958b-18f6e27aaade");

        allOrders.add(order);
    }

    public List<Order> findAllOrders() {

        return allOrders.stream().map(Order::new).collect(Collectors.toList());
    }
}
