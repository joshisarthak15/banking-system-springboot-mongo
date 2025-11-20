package com.banking.servicetest;

import com.document.Transaction;
import com.repository.TransactionRepository;
import com.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private TransactionService service;
    private TransactionRepository repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(TransactionRepository.class);

        service = new TransactionService();
        service.setTransactionRepository(repository);
    }

    @Test
    public void testGetTransactionsByAccount() {
        Transaction t = new Transaction();
        t.setSourceAccount("ACC1");

        List<Transaction> list = Arrays.asList(t);

        when(repository.findBySourceAccountOrDestinationAccount("ACC1","ACC1"))
                .thenReturn(list);

        List<Transaction> result = service.getTransactionsByAccount("ACC1");

        assertEquals(1, result.size());
    }
}
