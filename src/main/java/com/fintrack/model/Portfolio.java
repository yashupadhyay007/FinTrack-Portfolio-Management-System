package com.fintrack.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Portfolio {
    private BigDecimal cashBalance;
    private Map<String, Holding> holdings;
    private List<Transaction> transactions;

    public Portfolio() {
        this.cashBalance = BigDecimal.ZERO;
        this.holdings = new HashMap<>();
        this.transactions = new ArrayList<>();
    }

    public BigDecimal getCashBalance() { return cashBalance; }
    public void setCashBalance(BigDecimal cashBalance) { this.cashBalance = cashBalance; }

    public Map<String, Holding> getHoldings() { return holdings; }
    public List<Transaction> getTransactions() { return transactions; }

    public void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    
    public void addCash(BigDecimal amount) {
        this.cashBalance = this.cashBalance.add(amount);
    }

    public void subtractCash(BigDecimal amount) {
        this.cashBalance = this.cashBalance.subtract(amount);
    }
}
