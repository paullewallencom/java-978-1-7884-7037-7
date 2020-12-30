package com.acme.payments.api.services;

import com.acme.payments.lib.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {

    Transaction findTransactionById(String id);

    List<Transaction> findTransactions(Pageable pageable);

    Long findTransactionsCount();

    Transaction createTransaction(Transaction transaction);
}
