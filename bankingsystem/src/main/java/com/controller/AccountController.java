package com.controller;

import com.document.Account;
import com.document.Transaction;
import com.repository.AccountRepository;
import com.service.AccountService;
import com.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    // ------------------- Create Account -------------------
    @PostMapping
    public ApiResponse createAccount(@RequestParam String holderName) {
        Account account = accountService.createAccount(holderName);
        return new ApiResponse("Account created successfully", account, true);
    }

    // ------------------- Get Account -------------------
    @GetMapping("/{accountNumber}")
    public ApiResponse getAccount(@PathVariable String accountNumber) {
        Account account = accountService.getAccount(accountNumber);
        return new ApiResponse("Account details fetched", account, true);
    }

    // ------------------- Update Account Holder Name -------------------
    @PutMapping("/{accountNumber}")
    public ApiResponse updateAccount(@PathVariable String accountNumber,
                                     @RequestParam String holderName) {

        Account updated = accountService.updateAccount(accountNumber, holderName);
        return new ApiResponse("Account updated successfully", updated, true);
    }

    // ------------------- Delete Account -------------------
    @DeleteMapping("/{accountNumber}")
    public ApiResponse deleteAccount(@PathVariable String accountNumber) {
        accountService.deleteAccount(accountNumber);
        return new ApiResponse("Account deleted successfully", accountNumber, true);
    }

    // ------------------- Deposit -------------------
    @PutMapping("/{accountNumber}/deposit")
    public ApiResponse deposit(@PathVariable String accountNumber,
                               @RequestParam Double amount) {

        Transaction txn = accountService.deposit(accountNumber, amount);
        return new ApiResponse("Deposit successful", txn, true);
    }

    // ------------------- Withdraw -------------------
    @PutMapping("/{accountNumber}/withdraw")
    public ApiResponse withdraw(@PathVariable String accountNumber,
                                @RequestParam Double amount) {

        Transaction txn = accountService.withdraw(accountNumber, amount);
        return new ApiResponse("Withdrawal successful", txn, true);
    }

    // ------------------- Transfer -------------------
    @PostMapping("/transfer")
    public ApiResponse transfer(@RequestBody Map<String,Object> body) {

        String sourceAccount = body.get("sourceAccount").toString();
        String destinationAccount = body.get("destinationAccount").toString();
        Double amount = Double.parseDouble(body.get("amount").toString());

        Transaction txn = accountService.transfer(sourceAccount, destinationAccount, amount);

        return new ApiResponse("Transfer successful", txn, true);
    }


    // ------------------- Get Transactions For Account -------------------
    @GetMapping("/{accountNumber}/transactions")
    public ApiResponse getAccountTransactions(@PathVariable String accountNumber) {

        List<Transaction> transactions = accountService.getTransactions(accountNumber);
        return new ApiResponse("Transactions fetched", transactions, true);
    }

    @GetMapping("/{accountNumber}/exists")
    public ApiResponse exists(@PathVariable String accountNumber) {
        boolean exists = accountService.getAccount(accountNumber) != null;
        return new ApiResponse("Exists check", exists, true);
    }
}
