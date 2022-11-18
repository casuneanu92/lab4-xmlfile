package org.example.service;

import org.example.domain.Transaction;
import org.example.domain.validators.ValidatorException;
import org.example.repository.Repository;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class TransactionService {
    private Repository<Long, Transaction> repository;

    public TransactionService(Repository<Long, Transaction> repository) {
        this.repository = repository;
    }

    public void addTransaction(Transaction transaction) throws ValidatorException {
        repository.save(transaction);
    }

    public Set<Transaction> getAllTransactions() {
        Iterable<Transaction> transactions = repository.findAll();
        return StreamSupport.stream(transactions.spliterator(), false).collect(Collectors.toSet());
    }

    public  void updateTransaction(Transaction transaction) throws ValidatorException, IllegalAccessException {
        repository.update(transaction);
    }

    public void deleteTransaction(long id) throws IllegalAccessException {
        repository.delete(id);
    }
}
