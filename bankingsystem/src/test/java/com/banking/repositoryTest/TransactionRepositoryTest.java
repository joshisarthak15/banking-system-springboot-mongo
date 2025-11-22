package com.banking.repositoryTest;

import com.document.Transaction;
import com.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionRepositoryTest {

    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setup() {
        transactionRepository = Mockito.mock(TransactionRepository.class);
    }

    @Test
    public void testFindBySourceAccount() {
        Transaction t = new Transaction();
        t.setSourceAccount("SRC1");

        List<Transaction> list = Arrays.asList(t);

        when(transactionRepository.findBySourceAccount("SRC1"))
                .thenReturn(list);

        List<Transaction> result = transactionRepository.findBySourceAccount("SRC1");

        assertEquals(1, result.size());
        assertEquals("SRC1", result.get(0).getSourceAccount());
    }

    @Test
    public void testFindByDestinationAccount() {
        Transaction t = new Transaction();
        t.setDestinationAccount("DST1");

        List<Transaction> list = Arrays.asList(t);

        when(transactionRepository.findByDestinationAccount("DST1"))
                .thenReturn(list);

        List<Transaction> result = transactionRepository.findByDestinationAccount("DST1");

        assertEquals(1, result.size());
        assertEquals("DST1", result.get(0).getDestinationAccount());
    }
}
