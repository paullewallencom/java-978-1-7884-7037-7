package com.acme.orders.api.integrations;

import com.acme.customers.lib.v1.Customer;
import com.acme.orders.api.rest.v1.auth.User;

public interface CustomersClient {

    Customer findCustomerById(String id, User user);
}
