package com.acme.payments.api.services.exceptions;

/**
 * @author Tilen Faganel, Sunesis ltd.
 * @since 1.0.0
 */
public class EmptyPayloadException extends RuntimeException {

    private String resource;

    public EmptyPayloadException(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
