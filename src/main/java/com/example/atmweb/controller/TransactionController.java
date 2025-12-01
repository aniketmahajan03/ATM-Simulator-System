package com.example.atmweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.atmweb.model.Transaction;
import com.example.atmweb.service.TransactionService;

@RestController
@RequestMapping("/api/accounts")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    // ✅ Fetch Transaction History
    @GetMapping("/{accountNumber}/transactions")
    public List<Transaction> getTransactions(@PathVariable String accountNumber) {
        return transactionService.getAllTransactionsByAccountNumber(accountNumber);
    }
}
