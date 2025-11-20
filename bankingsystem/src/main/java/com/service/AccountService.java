package com.service;

import com.document.Account;
import com.document.Transaction;
import com.exception.AccountNotFoundException;
import com.exception.InsufficientBalanceException;
import com.exception.InvalidAmountException;
import com.repository.AccountRepository;
import com.repository.TransactionRepository;
import com.util.IdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    protected AccountRepository accountRepository;

    @Autowired
    protected TransactionRepository transactionRepository;

    public void setAccountRepository(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void setTransactionRepository(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    // ---------------------- Create Account ----------------------
    public Account createAccount(String holderName) {
        String accountNumber = IdGenerator.generateAccountNumber(holderName);

        Account account = new Account();
        account.setAccountNumber(accountNumber);
        account.setHolderName(holderName);
        account.setBalance(0.0);
        account.setStatus("ACTIVE");
        account.setCreatedAt(new Date());

        Account saved = accountRepository.save(account);

        log.info("Account created: {}", saved.getAccountNumber());

        return saved;
    }

    // ---------------------- Get Account ----------------------
    public Account getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException(accountNumber));
    }

    // ---------------------- Update Holder Name ----------------------
    public Account updateAccount(String accountNumber, String newHolderName) {
        Account account = getAccount(accountNumber);

        account.setHolderName(newHolderName);
        Account updated = accountRepository.save(account);

        log.info("Account updated: {}", accountNumber);

        return updated;
    }

    // ---------------------- Delete Account ----------------------
    public void deleteAccount(String accountNumber) {
        if (!accountRepository.existsByAccountNumber(accountNumber)) {
            throw new AccountNotFoundException(accountNumber);
        }

        accountRepository.deleteByAccountNumber(accountNumber);

        log.warn("Account deleted: {}", accountNumber);
    }

    // ---------------------- Deposit ----------------------
    public Transaction deposit(String accountNumber, Double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be > 0");
        }

        Account account = getAccount(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setTransactionId(IdGenerator.generateTransactionId());
        txn.setType("DEPOSIT");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(accountNumber);

        log.info("Deposit {} to {}", amount, accountNumber);

        return transactionRepository.save(txn);
    }

    // ---------------------- Withdraw ----------------------
    public Transaction withdraw(String accountNumber, Double amount) {

        if (amount <= 0) {
            throw new InvalidAmountException("Withdraw amount must be > 0");
        }

        Account account = getAccount(accountNumber);

        if (account.getBalance() < amount) {
            throw new InsufficientBalanceException(accountNumber);
        }

        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction txn = new Transaction();
        txn.setTransactionId(IdGenerator.generateTransactionId());
        txn.setType("WITHDRAW");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(accountNumber);

        log.info("Withdraw {} from {}", amount, accountNumber);

        return transactionRepository.save(txn);
    }

    // ---------------------- Transfer ----------------------
    public Transaction transfer(String sourceAcc, String destAcc, Double amount) {

        if (sourceAcc.equals(destAcc)) {
            throw new InvalidAmountException("Source and destination cannot be same");
        }

        if (amount <= 0) {
            throw new InvalidAmountException("Amount must be > 0");
        }

        Account src = getAccount(sourceAcc);
        Account dest = getAccount(destAcc);

        if (src.getBalance() < amount) {
            throw new InsufficientBalanceException(sourceAcc);
        }

        src.setBalance(src.getBalance() - amount);
        dest.setBalance(dest.getBalance() + amount);

        accountRepository.save(src);
        accountRepository.save(dest);

        Transaction txn = new Transaction();
        txn.setTransactionId(IdGenerator.generateTransactionId());
        txn.setType("TRANSFER");
        txn.setAmount(amount);
        txn.setTimestamp(new Date());
        txn.setStatus("SUCCESS");
        txn.setSourceAccount(sourceAcc);
        txn.setDestinationAccount(destAcc);

        log.info("Transfer {} from {} to {}", amount, sourceAcc, destAcc);

        return transactionRepository.save(txn);
    }

    // ---------------------- Get Transactions ----------------------
    public List<Transaction> getTransactions(String accountNumber) {
        getAccount(accountNumber); // validate

        return transactionRepository
                .findBySourceAccountOrDestinationAccount(accountNumber, accountNumber);
    }
}
