package com.acme.payments.api.rest.v1.resources;

import com.acme.payments.api.services.TransactionService;
import com.acme.payments.lib.Transaction;
import com.acme.payments.lib.response.CountResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Component
@Path("/transactions")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransactionResource {

    private final TransactionService transactionService;

    @Autowired
    public TransactionResource(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GET
    public Response getTransactions() {

        List<Transaction> transactions = transactionService.findTransactions();
        Long transactionsCount = transactionService.findTransactionsCount();

        return Response.ok().header("X-Total-Count", transactionsCount.toString()).entity(transactions).build();
    }

    @GET
    @Path("/count")
    public Response getTransactionsCount() {

        Long transactionsCount = transactionService.findTransactionsCount();

        CountResponse countResponse = new CountResponse();
        countResponse.setCount(transactionsCount);

        return Response.ok(countResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getTransaction(@PathParam("id") String id) {

        Transaction transaction = transactionService.findTransactionById(id);

        return Response.ok(transaction).build();
    }

    @POST
    public Response createCustomer(Transaction newTransaction) {

        Transaction transaction = transactionService.createTransaction(newTransaction);

        return Response.ok(transaction).build();
    }
}
