package com.acme.orders.api.rest.v1.auth;

import java.security.Principal;
import java.util.List;

public class User implements Principal {

    static class Access {

        private List<String> roles;

        public Access() {
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    private String token;
    private String email;

    private Access access;

    public User(String token, String email, Access access) {
        this.token = token;
        this.email = email;
        this.access = access;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String getName() {
        return email;
    }

    public Access getAccess() {
        return access;
    }
}
