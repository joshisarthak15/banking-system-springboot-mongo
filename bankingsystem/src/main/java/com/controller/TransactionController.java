package com.controller;

import com.document.Transaction;
import com.service.TransactionService;
import com.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // Get transactions for a single account
    @GetMapping("/{accountNumber}")
    public ApiResponse getTransactions(@PathVariable String accountNumber) {
        List<Transaction> list = transactionService.getTransactionsByAccount(accountNumber);
        return new ApiResponse("Transactions fetched", list, true);
    }
}
