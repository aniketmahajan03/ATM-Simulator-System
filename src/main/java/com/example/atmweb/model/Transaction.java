package com.example.atmweb.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private String type; // DEPOSIT, WITHDRAW, TRANSFER_IN, TRANSFER_OUT
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String description;

    public Transaction() {
    }

    // ✅ Correct constructor (matches all service calls)
    public Transaction(Account account, String type, BigDecimal amount, LocalDateTime timestamp, String description) {
        this.account = account;
        this.type = type;
        this.amount = amount;
        this.timestamp = timestamp;
        this.description = description;
    }

    // ✅ Overloaded constructor without description
    public Transaction(Account account, String type, BigDecimal amount, LocalDateTime timestamp) {
        this(account, type, amount, timestamp, null);
    }

    // ✅ Getters & Setters
    public Long getId() {
        return id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
