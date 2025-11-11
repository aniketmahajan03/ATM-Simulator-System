package com.example.atmweb.util;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.atmweb.model.Account;
import com.example.atmweb.repository.AccountRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final AccountRepository accountRepo;

    public DataLoader(AccountRepository accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public void run(String... args) throws Exception {
        if (accountRepo.count() == 0) {
            Account a1 = new Account("Aniket", "1001", new BigDecimal("5000.00"));
            Account a2 = new Account("Rahul", "1002", new BigDecimal("3000.00"));
            accountRepo.save(a1);
            accountRepo.save(a2);
            System.out.println("Seeded test accounts: 1001, 1002");
        }
    }
}
