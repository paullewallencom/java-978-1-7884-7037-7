package com.acme.orders.api.services;

import com.acme.orders.api.rest.v1.auth.User;
import com.acme.orders.lib.v1.Order;

import java.util.List;

public interface OrderService {

    Order findOrderById(String id);

    List<Order> findOrders(Integer limit, Integer offset);

    Long findOrdersCount();

    Order createOrder(Order order, User user);

    Order completeOrder(String id);

    Order cancelOrder(String id);
}
