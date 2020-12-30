package com.acme.payments.api.rest.v1.mappers;

import com.acme.payments.api.services.exceptions.ResourceNotFoundException;
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
public class ResourceNotFoundMapper implements ExceptionMapper<ResourceNotFoundException> {

    @Override
    public Response toResponse(ResourceNotFoundException exception) {

        ApiError error = new ApiError();
        error.setRef(UUID.randomUUID());
        error.setStatus(404);
        error.setCode("resource.not.found");

        return Response.status(Response.Status.NOT_FOUND).entity(error).build();
    }
}
