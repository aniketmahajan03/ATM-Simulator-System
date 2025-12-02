package com.example.atmweb.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;
import com.example.atmweb.repository.AccountRepository;
import com.example.atmweb.repository.TransactionRepository;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // ✅ CREATE ACCOUNT
    public Account createAccount(String holderName, String accountNumber, BigDecimal initialBalance) {
        Account account = new Account();
        account.setHolderName(holderName);
        account.setAccountNumber(accountNumber);
        account.setBalance(initialBalance);

        return accountRepository.save(account);
    }

    // ✅ FETCH ACCOUNT BY ACCOUNT NUMBER
    public Account getAccountByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() ->
                        new RuntimeException("Account not found: " + accountNumber));
    }

    // ✅ DEPOSIT
    public BigDecimal deposit(String accountNumber, BigDecimal amount) {
        Account account = getAccountByAccountNumber(accountNumber);

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                "DEPOSIT",
                amount,
                LocalDateTime.now(),
                "Deposit Successful"
        );
        transactionRepository.save(tx);

        return account.getBalance();
    }

    // ✅ WITHDRAW
    public BigDecimal withdraw(String accountNumber, BigDecimal amount) {
        Account account = getAccountByAccountNumber(accountNumber);

        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient Balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);

        Transaction tx = new Transaction(
                account,
                "WITHDRAW",
                amount,
                LocalDateTime.now(),
                "Withdraw Successful"
        );
        transactionRepository.save(tx);

        return account.getBalance();
    }

    // ✅ CHECK BALANCE
    public BigDecimal checkBalance(String accountNumber) {
        return getAccountByAccountNumber(accountNumber).getBalance();
    }

    // ✅ MINI STATEMENT (LAST 5 TRANSACTIONS)
   public List<Transaction> getMiniStatement(String accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber)
            .orElseThrow(() -> new RuntimeException("Account not found"));

    return transactionRepository.findTop5ByAccountOrderByTimestampDesc(account);
}

    // ✅ FULL TRANSACTION HISTORY (OPTIONAL)
    public List<Transaction> getHistory(String accountNumber) {
        Account account = getAccountByAccountNumber(accountNumber);
        return transactionRepository.findByAccount(account);
    }

}
