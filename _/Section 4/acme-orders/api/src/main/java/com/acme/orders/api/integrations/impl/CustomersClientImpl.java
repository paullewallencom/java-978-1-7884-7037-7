package com.acme.orders.api.integrations.impl;

import com.acme.customers.lib.v1.Customer;
import com.acme.orders.api.integrations.CustomersClient;
import com.acme.orders.api.rest.v1.auth.User;
import com.acme.orders.api.services.exceptions.ResourceNotFoundException;
import com.acme.orders.lib.v1.common.ApiError;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class CustomersClientImpl implements CustomersClient {

    private WebTarget webTarget;

    private final Timer findCustomerByIdTimer;

    public CustomersClientImpl(Client client, String baseUrl, MetricRegistry metricRegistry) {
        this.webTarget = client.target(baseUrl);

        this.findCustomerByIdTimer = metricRegistry.timer(CustomersClientImpl.class.getName() + ".request-find-customer-by-id");
    }

    @Override
    public Customer findCustomerById(String id, User user) {

        final Timer.Context context = findCustomerByIdTimer.time();

        try {
            return webTarget.path("customers").path(id).request(MediaType.APPLICATION_JSON_TYPE)
                    .header("Authorization", "Bearer " + user.getToken())
                    .get(Customer.class);
        } catch (WebApplicationException e) {

            ApiError error = e.getResponse().readEntity(ApiError.class);

            switch (error.getStatus()) {

                case 404:
                    throw new ResourceNotFoundException(Customer.class.getSimpleName(), id);
            }

            throw e;
        } finally {
            context.stop();
        }
    }
}
