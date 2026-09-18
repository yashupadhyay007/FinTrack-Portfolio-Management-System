package com.fintrack;

import com.fintrack.exception.InsufficientFundsException;
import com.fintrack.exception.InsufficientUnitsException;
import com.fintrack.exception.InvalidAmountException;
import com.fintrack.model.Portfolio;
import com.fintrack.model.Stock;
import com.fintrack.repository.PortfolioRepository;
import com.fintrack.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class TransactionServiceTest {

    private Portfolio portfolio;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio();
        // A simple dummy repository that does nothing on save
        PortfolioRepository dummyRepo = new PortfolioRepository() {
            @Override
            public Portfolio load() { return portfolio; }
            @Override
            public void save(Portfolio p) {}
        };
        transactionService = new TransactionService(portfolio, dummyRepo);
    }

    @Test
    void testDepositValidAmount() {
        transactionService.deposit(new BigDecimal("1000.50"), "Salary");
        assertEquals(new BigDecimal("1000.50"), portfolio.getCashBalance());
        assertEquals(1, portfolio.getTransactions().size());
    }

    @Test
    void testDepositNegativeAmountThrowsException() {
        assertThrows(InvalidAmountException.class, () -> {
            transactionService.deposit(new BigDecimal("-100"), "Bad");
        });
    }

    @Test
    void testWithdrawValidAmount() {
        transactionService.deposit(new BigDecimal("1000"), "Initial");
        transactionService.withdraw(new BigDecimal("200"), "Groceries");
        assertEquals(new BigDecimal("800"), portfolio.getCashBalance());
    }

    @Test
    void testWithdrawInsufficientFundsThrowsException() {
        transactionService.deposit(new BigDecimal("100"), "Initial");
        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.withdraw(new BigDecimal("200"), "Too much");
        });
    }

    @Test
    void testBuyAssetSuccessful() {
        transactionService.deposit(new BigDecimal("1000"), "Initial");
        Stock stock = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("150"));
        transactionService.buy(stock, new BigDecimal("2"), new BigDecimal("150"), "Buy Apple");

        assertEquals(new BigDecimal("700"), portfolio.getCashBalance());
        assertTrue(portfolio.getHoldings().containsKey("AAPL"));
        assertEquals(new BigDecimal("2"), portfolio.getHoldings().get("AAPL").getQuantity());
        assertEquals(new BigDecimal("300"), portfolio.getHoldings().get("AAPL").getInvestedAmount());
    }

    @Test
    void testBuyAssetInsufficientFunds() {
        transactionService.deposit(new BigDecimal("200"), "Initial");
        Stock stock = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("150"));
        assertThrows(InsufficientFundsException.class, () -> {
            transactionService.buy(stock, new BigDecimal("2"), new BigDecimal("150"), "Buy Apple");
        });
    }

    @Test
    void testSellAssetSuccessful() {
        transactionService.deposit(new BigDecimal("1000"), "Initial");
        Stock stock = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("150"));
        transactionService.buy(stock, new BigDecimal("2"), new BigDecimal("150"), "Buy Apple");
        
        transactionService.sell("AAPL", new BigDecimal("1"), new BigDecimal("160"), "Sell Apple");
        
        assertEquals(new BigDecimal("860"), portfolio.getCashBalance());
        assertEquals(new BigDecimal("1"), portfolio.getHoldings().get("AAPL").getQuantity());
    }

    @Test
    void testSellAssetInsufficientUnits() {
        transactionService.deposit(new BigDecimal("1000"), "Initial");
        Stock stock = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("150"));
        transactionService.buy(stock, new BigDecimal("1"), new BigDecimal("150"), "Buy Apple");
        
        assertThrows(InsufficientUnitsException.class, () -> {
            transactionService.sell("AAPL", new BigDecimal("2"), new BigDecimal("160"), "Sell Too Much Apple");
        });
    }
}
