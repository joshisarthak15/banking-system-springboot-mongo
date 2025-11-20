package com.service;

import com.document.Transaction;
import com.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    protected TransactionRepository transactionRepository;

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }


    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        return transactionRepository
                .findBySourceAccountOrDestinationAccount(accountNumber, accountNumber);
    }
}
