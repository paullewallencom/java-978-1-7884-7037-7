package com.acme.orders.api.tests;

import com.acme.orders.api.rest.v1.RestApplication;
import com.acme.orders.api.rest.v1.RestConfiguration;
import com.acme.orders.api.rest.v1.providers.JacksonProvider;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;
import org.junit.ClassRule;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class OrderResourceTest {

    @ClassRule
    public static final DropwizardAppRule<RestConfiguration> RULE =
            new DropwizardAppRule<>(RestApplication.class,
                    ResourceHelpers.resourceFilePath("config.yml"));

    @Test
    public void testFindOrders() {

        Client client = ClientBuilder.newClient().register(JacksonProvider.class);

        Response response = client.target(
                String.format("http://localhost:%d/orders", RULE.getLocalPort()))
                .request()
                .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }
}
