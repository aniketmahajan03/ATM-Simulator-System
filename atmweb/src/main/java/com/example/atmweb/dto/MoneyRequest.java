package com.example.atmweb.dto;

import java.math.BigDecimal;

public class MoneyRequest {
    private BigDecimal amount;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}

