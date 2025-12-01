package com.example.atmweb.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;
import com.example.atmweb.repository.AccountRepository;
import com.example.atmweb.repository.TransactionRepository;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    // ✅ Create new account
    public Account createAccount(String holderName, String accountNumber, BigDecimal initialBalance) {
        Account account = new Account();
        account.setHolderName(holderName);
        account.setAccountNumber(accountNumber);
        account.setBalance(initialBalance);
        return accountRepository.save(account);
    }

    // ✅ Get account by ID
    public Account getAccount(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
    }

    // ✅ Get account balance by ID
    public BigDecimal getBalance(Long id) {
        Account account = getAccount(id);
        return account.getBalance();
    }

    // ✅ Deposit money
// ✅ Deposit money
@Transactional
public Account deposit(Long id, BigDecimal amount) {
    Account account = getAccount(id);

    // Update balance
    account.setBalance(account.getBalance().add(amount));

    // Save account update
    accountRepository.save(account);

    // Record transaction
    Transaction txn = new Transaction(account, "DEPOSIT", amount, LocalDateTime.now(), "Deposit");
    transactionRepository.save(txn);

    return account;
}

// ✅ Withdraw money
@Transactional
public Account withdraw(Long id, BigDecimal amount) {
    Account account = getAccount(id);

    if (account.getBalance().compareTo(amount) < 0) {
        throw new RuntimeException("Insufficient balance!");
    }

    // Update balance
    account.setBalance(account.getBalance().subtract(amount));

    // Save account update
    accountRepository.save(account);

    // Record transaction
    Transaction txn = new Transaction(account, "WITHDRAW", amount, LocalDateTime.now(), "Withdraw");
    transactionRepository.save(txn);

    return account;
}



    // ✅ Transfer money between accounts
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        Account from = getAccount(fromId);
        Account to = getAccount(toId);

        if (from.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance in source account!");
        }

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));

        accountRepository.save(from);
        accountRepository.save(to);

        // Transfer
transactionRepository.save(new Transaction(from, "TRANSFER_OUT", amount, LocalDateTime.now(),
        "Transferred to " + to.getAccountNumber()));
transactionRepository.save(new Transaction(to, "TRANSFER_IN", amount, LocalDateTime.now(),
        "Received from " + from.getAccountNumber()));

    }

    // ✅ Transaction history
    public List<Transaction> getHistory(Long accountId) {
        Account account = getAccount(accountId);
        return transactionRepository.findByAccount(account);
    }

    // ✅ Check balance by account number (used in controller)
    public String getBalanceByAccountNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .map(account -> "💰 Current balance for " + account.getHolderName() +
                        " is ₹" + account.getBalance())
                .orElse("❌ Account not found for account number: " + accountNumber);
    }

    public List<Transaction> getLastTransactions(Long accountId, int limit) {
    Account account = getAccount(accountId);
    return transactionRepository
            .findTop5ByAccountOrderByTimestampDesc(account);
}


}
