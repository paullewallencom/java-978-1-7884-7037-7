package com.acme.orders.api.services.health;

import com.codahale.metrics.health.HealthCheck;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class IntegrationHealthCheck extends HealthCheck {

    private final WebTarget webTarget;

    public IntegrationHealthCheck(Client client, String url) {
        this.webTarget = client.target(url);
    }

    @Override
    protected Result check() throws Exception {

        try {
            Response response = webTarget.request().get();

            if (!response.getStatusInfo().getFamily().equals(Response.Status.Family.SUCCESSFUL)) {

                return Result.unhealthy("Received status: " + response.getStatus());
            }

            return Result.healthy();
        } catch (Exception e) {

            return Result.unhealthy(e.getMessage());
        }
    }
}
