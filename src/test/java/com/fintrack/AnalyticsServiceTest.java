package com.fintrack;

import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;
import com.fintrack.model.Stock;
import com.fintrack.service.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnalyticsServiceTest {

    private Portfolio portfolio;
    private AnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio();
        analyticsService = new AnalyticsService(portfolio);
    }

    @Test
    void testCalculationsWithEmptyPortfolio() {
        assertEquals(BigDecimal.ZERO, analyticsService.getTotalInvestedAmount());
        assertEquals(BigDecimal.ZERO, analyticsService.getCurrentPortfolioValue());
        assertEquals(BigDecimal.ZERO, analyticsService.getTotalProfitLoss());
        assertEquals(BigDecimal.ZERO, analyticsService.getReturnPercentage());
    }

    @Test
    void testCalculationsWithHoldings() {
        Stock stock1 = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("150")); // old price 100
        Holding h1 = new Holding(stock1, new BigDecimal("10"), new BigDecimal("100")); // invested 1000, value 1500
        
        Stock stock2 = new Stock("MSFT", "MSFT", "Microsoft", new BigDecimal("250")); // old price 300
        Holding h2 = new Holding(stock2, new BigDecimal("4"), new BigDecimal("300")); // invested 1200, value 1000

        portfolio.getHoldings().put("AAPL", h1);
        portfolio.getHoldings().put("MSFT", h2);

        assertEquals(new BigDecimal("2200"), analyticsService.getTotalInvestedAmount());
        assertEquals(new BigDecimal("2500"), analyticsService.getCurrentPortfolioValue());
        assertEquals(new BigDecimal("300"), analyticsService.getTotalProfitLoss());
        
        // Return % = 300 / 2200 = 0.1364 (rounded to 4 decimal places)
        assertEquals(new BigDecimal("0.1364"), analyticsService.getReturnPercentage());
        
        // Allocation AAPL = 1500 / 2500 = 0.60
        assertEquals(new BigDecimal("0.6000"), analyticsService.getAssetAllocation("AAPL"));
    }
    @Test
    void testRealizedProfitFromSellTransaction() {
        com.fintrack.model.Transaction buy = new com.fintrack.model.Transaction("1", com.fintrack.enums.TransactionType.BUY, "NDAQFUND", new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("20000"), java.time.LocalDateTime.now(), "");
        com.fintrack.model.Transaction sell = new com.fintrack.model.Transaction("2", com.fintrack.enums.TransactionType.SELL, "NDAQFUND", new BigDecimal("50"), new BigDecimal("120"), new BigDecimal("6000"), java.time.LocalDateTime.now(), "");
        portfolio.addTransaction(buy);
        portfolio.addTransaction(sell);
        
        Stock stock = new Stock("NDAQFUND", "NDAQFUND", "Nasdaq", new BigDecimal("100"));
        Holding h = new Holding(stock, new BigDecimal("150"), new BigDecimal("100"));
        portfolio.getHoldings().put("NDAQFUND", h);

        assertEquals(new BigDecimal("15000"), analyticsService.getTotalInvestedAmount());
        assertEquals(new BigDecimal("15000"), analyticsService.getCurrentPortfolioValue());
        
        // Unrealized is 0. Realized is 50 * (120 - 100) = 1000. Total = 1000.
        // Wait, 6000 revenue - 50*100 cost = 1000.
        // Note: use stripTrailingZeros() or compareTo to avoid exact scale issues if they arise.
        assertEquals(0, new BigDecimal("1000").compareTo(analyticsService.getTotalProfitLoss()));
        
        // Return % = 1000 / 15000 = 0.0667 (rounded to 4 decimal places)
        assertEquals(0, new BigDecimal("0.0667").compareTo(analyticsService.getReturnPercentage()));
    }
}

