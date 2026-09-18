package com.fintrack.service;

import com.fintrack.enums.TransactionType;
import com.fintrack.exception.InsufficientFundsException;
import com.fintrack.exception.InsufficientUnitsException;
import com.fintrack.exception.InvalidAmountException;
import com.fintrack.exception.InvalidTransactionException;
import com.fintrack.model.Asset;
import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;
import com.fintrack.model.Transaction;
import com.fintrack.repository.PortfolioRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionService {
    private final Portfolio portfolio;
    private final PortfolioRepository repository;

    public TransactionService(Portfolio portfolio, PortfolioRepository repository) {
        this.portfolio = portfolio;
        this.repository = repository;
    }

    public void deposit(BigDecimal amount, String notes) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Deposit amount must be positive.");
        }
        
        portfolio.addCash(amount);
        Transaction tx = new Transaction(
            UUID.randomUUID().toString(),
            TransactionType.DEPOSIT,
            null, null, null,
            amount,
            LocalDateTime.now(),
            notes
        );
        portfolio.addTransaction(tx);
        repository.save(portfolio);
    }

    public void withdraw(BigDecimal amount, String notes) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be positive.");
        }
        if (portfolio.getCashBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient cash balance for withdrawal.");
        }

        portfolio.subtractCash(amount);
        Transaction tx = new Transaction(
            UUID.randomUUID().toString(),
            TransactionType.WITHDRAW,
            null, null, null,
            amount,
            LocalDateTime.now(),
            notes
        );
        portfolio.addTransaction(tx);
        repository.save(portfolio);
    }

    public void buy(Asset asset, BigDecimal quantity, BigDecimal price, String notes) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0 || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Quantity and price must be positive.");
        }
        
        BigDecimal totalCost = quantity.multiply(price);
        if (portfolio.getCashBalance().compareTo(totalCost) < 0) {
            throw new InsufficientFundsException("Insufficient cash balance for purchase. Cost: " + totalCost + ", Available: " + portfolio.getCashBalance());
        }

        portfolio.subtractCash(totalCost);

        Holding holding = portfolio.getHoldings().get(asset.getId());
        if (holding == null) {
            holding = new Holding(asset, BigDecimal.ZERO, BigDecimal.ZERO);
            portfolio.getHoldings().put(asset.getId(), holding);
        }
        holding.addQuantity(quantity, price);

        Transaction tx = new Transaction(
            UUID.randomUUID().toString(),
            TransactionType.BUY,
            asset.getId(),
            quantity,
            price,
            totalCost,
            LocalDateTime.now(),
            notes
        );
        portfolio.addTransaction(tx);
        repository.save(portfolio);
    }

    public void sell(String assetId, BigDecimal quantity, BigDecimal price, String notes) {
        if (quantity.compareTo(BigDecimal.ZERO) <= 0 || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAmountException("Quantity and price must be positive.");
        }

        Holding holding = portfolio.getHoldings().get(assetId);
        if (holding == null || holding.getQuantity().compareTo(quantity) < 0) {
            throw new InsufficientUnitsException("Insufficient asset units to sell.");
        }

        BigDecimal totalRevenue = quantity.multiply(price);
        portfolio.addCash(totalRevenue);

        holding.removeQuantity(quantity);
        if (holding.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
            portfolio.getHoldings().remove(assetId);
        }

        Transaction tx = new Transaction(
            UUID.randomUUID().toString(),
            TransactionType.SELL,
            assetId,
            quantity,
            price,
            totalRevenue,
            LocalDateTime.now(),
            notes
        );
        portfolio.addTransaction(tx);
        repository.save(portfolio);
    }
}
