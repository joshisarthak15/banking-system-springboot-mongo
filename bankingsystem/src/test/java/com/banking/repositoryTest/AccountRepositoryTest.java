package com.banking.repositoryTest;

import com.document.Account;
import com.repository.AccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountRepositoryTest {

    private AccountRepository accountRepository;

    @BeforeEach
    public void setup() {
        accountRepository = Mockito.mock(AccountRepository.class);
    }

    @Test
    public void testFindByAccountNumber() {
        Account acc = new Account();
        acc.setAccountNumber("ACC100");

        when(accountRepository.findByAccountNumber("ACC100"))
                .thenReturn(Optional.of(acc));

        Optional<Account> result = accountRepository.findByAccountNumber("ACC100");

        assertTrue(result.isPresent());
        assertEquals("ACC100", result.get().getAccountNumber());
    }

    @Test
    public void testExistsByAccountNumber() {
        when(accountRepository.existsByAccountNumber("ACC200")).thenReturn(true);

        boolean exists = accountRepository.existsByAccountNumber("ACC200");

        assertTrue(exists);
    }

    @Test
    public void testDeleteByAccountNumber() {
        doNothing().when(accountRepository).deleteByAccountNumber("ACC300");

        accountRepository.deleteByAccountNumber("ACC300");

        verify(accountRepository, times(1)).deleteByAccountNumber("ACC300");
    }
}
