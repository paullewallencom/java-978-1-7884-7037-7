package com.acme.payments.api.services.exceptions;

/**
 * @author Tilen Faganel, Sunesis ltd.
 * @since 1.3.0
 */
public class GeneralServiceException extends RuntimeException {

    public GeneralServiceException(Throwable cause) {
        super(cause);
    }
}
