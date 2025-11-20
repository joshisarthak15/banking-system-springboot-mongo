package com.banking.controllertest;

import com.controller.AccountController;
import com.document.Account;
import com.document.Transaction;
import com.service.AccountService;
import com.util.ApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    private AccountController controller;
    private AccountService service;

    @BeforeEach
    public void setup() {
        service = Mockito.mock(AccountService.class);
        controller = new AccountController();

        // inject mock
        controller.setAccountService(service);
    }

    @Test
    public void testGetAccount() {
        Account acc = new Account();
        acc.setAccountNumber("ACC1");

        when(service.getAccount("ACC1")).thenReturn(acc);

        ApiResponse response = controller.getAccount("ACC1");

        assertTrue(response.isSuccess());
        assertEquals("ACC1", ((Account) response.getData()).getAccountNumber());
    }

    @Test
    public void testCreateAccount() {
        Account acc = new Account();
        acc.setHolderName("John");

        when(service.createAccount("John")).thenReturn(acc);

        ApiResponse response = controller.createAccount("John");

        assertEquals("John", ((Account) response.getData()).getHolderName());
    }

    @Test
    public void testDeleteAccount() {
        doNothing().when(service).deleteAccount("ACC1");

        ApiResponse response = controller.deleteAccount("ACC1");

        assertTrue(response.isSuccess());
    }

    @Test
    public void testDeposit() {
        Transaction txn = new Transaction();
        txn.setAmount(200.0);

        when(service.deposit("ACC1", 200.0)).thenReturn(txn);

        ApiResponse response = controller.deposit("ACC1", 200.0);

        assertEquals(200.0, ((Transaction) response.getData()).getAmount());
    }
}
