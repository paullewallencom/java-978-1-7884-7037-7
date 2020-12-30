package com.acme.orders.api.rest.v1.mappers;

import com.acme.orders.lib.v1.common.ApiError;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.UUID;

/**
 * @author Tilen Faganel, Sunesis ltd.
 * @since 1.0.0
 */
@Provider
public class FormatNotAcceptableMapper implements ExceptionMapper<NotAcceptableException> {

    @Override
    public Response toResponse(NotAcceptableException exception) {

        ApiError error = new ApiError();
        error.setRef(UUID.randomUUID());
        error.setStatus(400);
        error.setCode("media.not.supported");

        return Response.status(Response.Status.BAD_REQUEST).type(MediaType.APPLICATION_JSON).entity(error).build();
    }
}
