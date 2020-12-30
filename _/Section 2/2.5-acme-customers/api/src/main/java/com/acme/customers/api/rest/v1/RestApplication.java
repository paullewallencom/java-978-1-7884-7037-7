package com.acme.customers.api.rest.v1;

import com.acme.customers.api.rest.v1.providers.JacksonProvider;
import com.acme.customers.api.rest.v1.resources.CustomerResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class RestApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {

        Set<Class<?>> classes = new HashSet<>();

        classes.add(JacksonJsonProvider.class);

        classes.add(JacksonProvider.class);

        classes.add(CustomerResource.class);

        return classes;
    }
}
