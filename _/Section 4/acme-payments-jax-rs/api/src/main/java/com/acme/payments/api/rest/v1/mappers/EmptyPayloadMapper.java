package com.acme.payments.api.rest.v1.mappers;

import com.acme.payments.api.services.exceptions.EmptyPayloadException;
import com.acme.payments.lib.common.ApiError;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

/**
 * @author Tilen Faganel, Sunesis ltd.
 * @since 1.0.0
 */
@Provider
public class EmptyPayloadMapper implements ExceptionMapper<EmptyPayloadException> {

    @Override
    public Response toResponse(EmptyPayloadException exception) {

        ApiError error = new ApiError();
        error.setRef(UUID.randomUUID());
        error.setStatus(400);
        error.setCode("resource.empty.payload");

        return Response.status(Response.Status.BAD_REQUEST).entity(error).build();
    }
}
