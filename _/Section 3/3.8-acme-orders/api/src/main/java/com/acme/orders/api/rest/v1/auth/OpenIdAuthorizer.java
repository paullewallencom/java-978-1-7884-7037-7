package com.acme.orders.api.rest.v1.auth;

import io.dropwizard.auth.Authorizer;

public class OpenIdAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String s) {
        return user.getAccess().getRoles().contains(s);
    }
}
