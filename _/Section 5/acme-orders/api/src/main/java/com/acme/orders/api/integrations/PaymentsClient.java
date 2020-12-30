package com.acme.orders.api.integrations;

import com.acme.orders.api.models.db.OrderEntity;
import com.acme.payments.lib.Transaction;

public interface PaymentsClient {

    Transaction createTransaction(OrderEntity orderEntity);
}
