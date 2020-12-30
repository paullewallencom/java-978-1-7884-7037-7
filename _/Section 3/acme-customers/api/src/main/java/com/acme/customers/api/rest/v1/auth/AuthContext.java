package com.acme.customers.api.rest.v1.auth;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthContext {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
