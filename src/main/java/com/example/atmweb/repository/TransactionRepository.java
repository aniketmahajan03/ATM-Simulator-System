package com.example.atmweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ Fix for your error
    List<Transaction> findByAccount(Account account);

    // ✅ Optional: for recent transactions
    List<Transaction> findTop5ByAccountOrderByTimestampDesc(Account account);
}
