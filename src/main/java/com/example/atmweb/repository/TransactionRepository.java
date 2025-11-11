package com.example.atmweb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.atmweb.model.Account;
import com.example.atmweb.model.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // ✅ Custom finder method for transaction history of an account
    List<Transaction> findByAccount(Account account);
}
