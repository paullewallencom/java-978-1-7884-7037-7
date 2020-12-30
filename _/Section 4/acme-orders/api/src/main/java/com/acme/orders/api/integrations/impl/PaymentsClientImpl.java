package com.acme.orders.api.integrations.impl;

import com.acme.orders.api.integrations.PaymentsClient;
import com.acme.orders.api.models.db.OrderEntity;
import com.acme.orders.api.models.db.OrderItemEntity;
import com.acme.orders.lib.v1.Order;
import com.acme.orders.lib.v1.OrderItem;
import com.acme.payments.lib.Transaction;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

public class PaymentsClientImpl implements PaymentsClient {

    private WebTarget webTarget;

    public PaymentsClientImpl(Client client, String baseUrl) {
        this.webTarget = client.target(baseUrl);
    }

    @Override
    public Transaction createTransaction(OrderEntity order) {

        Transaction transaction = new Transaction();
        transaction.setNonce("TEST");
        transaction.setCurrency("EUR");
        transaction.setCustomerId(order.getCustomerId());
        transaction.setOrderId(order.getId());
        transaction.setAmount(order.getCart().stream().map(OrderItemEntity::getAmount).reduce(BigDecimal.ZERO, BigDecimal::add));

        return webTarget.path("transactions").request(MediaType.APPLICATION_JSON_TYPE)
                .post(Entity.json(transaction), Transaction.class);
    }
}
