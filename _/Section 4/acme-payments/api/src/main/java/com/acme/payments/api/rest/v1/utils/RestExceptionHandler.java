package com.acme.payments.api.rest.v1.utils;

import com.acme.payments.api.services.exceptions.EmptyPayloadException;
import com.acme.payments.api.services.exceptions.GeneralServiceException;
import com.acme.payments.api.services.exceptions.IdMismatchException;
import com.acme.payments.api.services.exceptions.ResourceNotFoundException;
import com.acme.payments.lib.common.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    protected ResponseEntity notFound(ResourceNotFoundException ex, WebRequest request) {

        ApiError error = new ApiError();
        error.setStatus(404);
        error.setCode("resource.not.found");

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(EmptyPayloadException.class)
    protected ResponseEntity emptyPayload(EmptyPayloadException ex, WebRequest request) {

        ApiError error = new ApiError();
        error.setStatus(400);
        error.setCode("resource.empty.payload");

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IdMismatchException.class)
    protected ResponseEntity idMismatch(IdMismatchException ex, WebRequest request) {

        ApiError error = new ApiError();
        error.setStatus(400);
        error.setCode("resource.id.mismatch");

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(GeneralServiceException.class)
    protected ResponseEntity general(GeneralServiceException ex, WebRequest request) {

        ApiError error = new ApiError();
        error.setStatus(500);
        error.setCode("internal.error");

        return handleExceptionInternal(ex, error, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
