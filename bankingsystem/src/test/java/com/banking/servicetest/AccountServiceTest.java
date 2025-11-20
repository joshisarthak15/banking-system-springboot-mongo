package com.banking.servicetest;

import com.document.Account;
import com.document.Transaction;
import com.exception.AccountNotFoundException;
import com.exception.InsufficientBalanceException;
import com.exception.InvalidAmountException;
import com.repository.AccountRepository;
import com.repository.TransactionRepository;
import com.service.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    private AccountService service;
    private AccountRepository accountRepo;
    private TransactionRepository transactionRepo;

    @BeforeEach
    public void setup() {
        accountRepo = Mockito.mock(AccountRepository.class);
        transactionRepo = Mockito.mock(TransactionRepository.class);

        service = new AccountService();
        service.setAccountRepository(accountRepo);
        service.setTransactionRepository(transactionRepo);

    }

    // ------------------ CREATE ACCOUNT ------------------
    @Test
    public void testCreateAccount() {
        when(accountRepo.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));

        Account acc = service.createAccount("John Doe");

        assertNotNull(acc);
        assertEquals("John Doe", acc.getHolderName());
        assertEquals("ACTIVE", acc.getStatus());
    }

    // ------------------ GET ACCOUNT ------------------
    @Test
    public void testGetAccountSuccess() {
        Account acc = new Account();
        acc.setAccountNumber("ACC123");

        when(accountRepo.findByAccountNumber("ACC123"))
                .thenReturn(Optional.of(acc));

        Account result = service.getAccount("ACC123");

        assertNotNull(result);
        assertEquals("ACC123", result.getAccountNumber());
    }

    @Test
    public void testGetAccountNotFound() {
        when(accountRepo.findByAccountNumber("NOPE"))
                .thenReturn(Optional.empty());

        assertThrows(AccountNotFoundException.class,
                () -> service.getAccount("NOPE"));
    }

    // ------------------ DEPOSIT ------------------
    @Test
    public void testDepositSuccess() {
        Account acc = new Account();
        acc.setAccountNumber("A1");
        acc.setBalance(500.0);

        when(accountRepo.findByAccountNumber("A1"))
                .thenReturn(Optional.of(acc));

        when(accountRepo.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Transaction txn = service.deposit("A1", 200.0);

        assertNotNull(txn);
        assertEquals("A1", txn.getSourceAccount());
        assertEquals(700.0, acc.getBalance());
    }

    @Test
    public void testDepositInvalid() {
        assertThrows(InvalidAmountException.class,
                () -> service.deposit("A1", 0.0));
    }

    // ------------------ WITHDRAW ------------------
    @Test
    public void testWithdrawSuccess() {
        Account acc = new Account();
        acc.setAccountNumber("A1");
        acc.setBalance(500.0);

        when(accountRepo.findByAccountNumber("A1"))
                .thenReturn(Optional.of(acc));
        when(accountRepo.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Transaction txn = service.withdraw("A1", 200.0);

        assertNotNull(txn);
        assertEquals(300.0, acc.getBalance());
    }

    @Test
    public void testWithdrawInsufficientBalance() {
        Account acc = new Account();
        acc.setAccountNumber("A1");
        acc.setBalance(100.0);

        when(accountRepo.findByAccountNumber("A1"))
                .thenReturn(Optional.of(acc));

        assertThrows(InsufficientBalanceException.class,
                () -> service.withdraw("A1", 200.0));
    }

    // ------------------ TRANSFER ------------------
    @Test
    public void testTransferSuccess() {

        Account src = new Account(null,"SRC","John",500.0,"ACTIVE",new Date());
        Account dest = new Account(null,"DST","Mark",300.0,"ACTIVE",new Date());

        when(accountRepo.findByAccountNumber("SRC")).thenReturn(Optional.of(src));
        when(accountRepo.findByAccountNumber("DST")).thenReturn(Optional.of(dest));

        when(accountRepo.save(any(Account.class))).thenAnswer(inv -> inv.getArgument(0));
        when(transactionRepo.save(any(Transaction.class))).thenAnswer(inv -> inv.getArgument(0));

        Transaction txn = service.transfer("SRC", "DST", 200.0);

        assertNotNull(txn);
        assertEquals("SRC", txn.getSourceAccount());
        assertEquals("DST", txn.getDestinationAccount());
        assertEquals(300.0, src.getBalance());
        assertEquals(500.0, dest.getBalance());
    }

    @Test
    public void testTransferInvalidAmount() {
        assertThrows(InvalidAmountException.class,
                () -> service.transfer("SRC", "DST", -50.0));
    }

    @Test
    public void testTransferSameAccount() {
        assertThrows(InvalidAmountException.class,
                () -> service.transfer("A1", "A1", 100.0));
    }

}
