package com.acme.payments.api.rest.v1;

import com.acme.payments.api.rest.v1.mappers.EmptyPayloadMapper;
import com.acme.payments.api.rest.v1.mappers.GeneralMapper;
import com.acme.payments.api.rest.v1.mappers.IdMismatchMapper;
import com.acme.payments.api.rest.v1.mappers.ResourceNotFoundMapper;
import com.acme.payments.api.rest.v1.resources.TransactionResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(TransactionResource.class);

        register(EmptyPayloadMapper.class);
        register(GeneralMapper.class);
        register(IdMismatchMapper.class);
        register(ResourceNotFoundMapper.class);
    }
}
