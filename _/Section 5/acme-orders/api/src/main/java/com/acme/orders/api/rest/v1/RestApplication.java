package com.acme.orders.api.rest.v1;

import com.acme.orders.api.integrations.CatalogueClient;
import com.acme.orders.api.integrations.impl.CatalogueClientImpl;
import com.acme.orders.api.integrations.impl.CustomersClientImpl;
import com.acme.orders.api.integrations.impl.PaymentsClientImpl;
import com.acme.orders.api.integrations.mock.CatalogueClientMock;
import com.acme.orders.api.models.OrderDAO;
import com.acme.orders.api.models.db.OrderEntity;
import com.acme.orders.api.models.db.OrderItemEntity;
import com.acme.orders.api.rest.v1.auth.OpenIdAuthenticator;
import com.acme.orders.api.rest.v1.auth.OpenIdAuthorizer;
import com.acme.orders.api.rest.v1.auth.User;
import com.acme.orders.api.rest.v1.mappers.*;
import com.acme.orders.api.rest.v1.providers.JacksonProvider;
import com.acme.orders.api.rest.v1.resources.OrderResource;
import com.acme.orders.api.services.OrderService;
import com.acme.orders.api.services.health.IntegrationHealthCheck;
import com.acme.orders.api.services.impl.OrderServiceImpl;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.auth.oauth.OAuthCredentialAuthFilter;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RestApplication extends Application<RestConfiguration> {

    public static void main(String args[]) throws Exception {
        new RestApplication().run(args);
    }

    private final HibernateBundle<RestConfiguration> hibernate = new HibernateBundle<RestConfiguration>(OrderEntity.class, OrderItemEntity.class) {

        @Override
        public DataSourceFactory getDataSourceFactory(RestConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    @Override
    public void initialize(Bootstrap<RestConfiguration> bootstrap) {

        bootstrap.setConfigurationSourceProvider(
                new SubstitutingSourceProvider(bootstrap.getConfigurationSourceProvider(),
                        new EnvironmentVariableSubstitutor(false)
                )
        );

        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(RestConfiguration configuration, Environment environment) throws Exception {

        environment.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        environment.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        environment.getObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        environment.getObjectMapper().configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        environment.getObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        environment.getObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL);

        Client client = ClientBuilder.newClient();

        CatalogueClient catalogueClient = configuration.getCatalogueMock() ?
                new CatalogueClientMock() :
                new CatalogueClientImpl(configuration.getCatalogueUrl());


        OrderService orderService = new OrderServiceImpl(
                new OrderDAO(hibernate.getSessionFactory(), environment.metrics()),
                environment.metrics(),
                new CustomersClientImpl(client, configuration.getCustomersUrl()),
                catalogueClient,
                new PaymentsClientImpl(client, configuration.getPaymentsUrl())
        );

        environment.jersey().register(new OrderResource(orderService));

        environment.jersey().register(EmptyPayloadMapper.class);
        environment.jersey().register(GeneralMapper.class);
        environment.jersey().register(IdMismatchMapper.class);
        environment.jersey().register(ResourceNotFoundMapper.class);
        environment.jersey().register(OrderServiceMapper.class);


        environment.jersey().register(new AuthDynamicFeature(
                new OAuthCredentialAuthFilter.Builder<User>()
                        .setAuthenticator(new OpenIdAuthenticator(getAuthAlgorithm(configuration.getAuthPublicKey())))
                        .setAuthorizer(new OpenIdAuthorizer())
                        .setPrefix("Bearer")
                        .buildAuthFilter()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        Client healthClient = ClientBuilder.newClient()
                .property(ClientProperties.CONNECT_TIMEOUT, 1_000)
                .property(ClientProperties.READ_TIMEOUT, 1_000);

        environment.healthChecks().register("payments-api",
                new IntegrationHealthCheck(healthClient, configuration.getPaymentsUrl() + "/health"));
    }

    private Algorithm getAuthAlgorithm(String publicKey) throws NoSuchAlgorithmException, InvalidKeySpecException {

        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKey));

        RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);

        return Algorithm.RSA256(pubKey, null);
    }
}
