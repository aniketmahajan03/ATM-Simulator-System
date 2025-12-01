package com.example.atmweb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;
import com.example.atmweb.repository.AccountRepository;
import com.example.atmweb.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    // ✅ Used for Transaction History
    public List<Transaction> getAllTransactionsByAccountNumber(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return transactionRepository.findByAccount(account);
    }

    // ✅ Used for PDF Download
    public List<Transaction> getTransactionsForPdf(String accountNumber) {

        Account account = accountRepository
                .findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        return transactionRepository.findByAccount(account);
    }
}
