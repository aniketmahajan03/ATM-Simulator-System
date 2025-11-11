package com.example.atmweb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.atmweb.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);
}
