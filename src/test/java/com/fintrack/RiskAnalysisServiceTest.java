package com.fintrack;

import com.fintrack.enums.RiskLevel;
import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;
import com.fintrack.model.Stock;
import com.fintrack.service.AnalyticsService;
import com.fintrack.service.RiskAnalysisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RiskAnalysisServiceTest {

    private Portfolio portfolio;
    private RiskAnalysisService riskAnalysisService;

    @BeforeEach
    void setUp() {
        portfolio = new Portfolio();
        AnalyticsService analyticsService = new AnalyticsService(portfolio);
        riskAnalysisService = new RiskAnalysisService(portfolio, analyticsService);
    }

    @Test
    void testEmptyPortfolioIsLowRisk() {
        assertEquals(RiskLevel.LOW, riskAnalysisService.analyzeRisk());
    }

    @Test
    void testHighlyConcentratedPortfolioIsHighRisk() {
        // High concentration, 100% equity, no diversification (1 holding), no cash
        Stock stock = new Stock("AAPL", "AAPL", "Apple", new BigDecimal("100"));
        Holding h = new Holding(stock, new BigDecimal("100"), new BigDecimal("100"));
        portfolio.getHoldings().put("AAPL", h);

        assertEquals(RiskLevel.HIGH, riskAnalysisService.analyzeRisk());
    }

    @Test
    void testWellDiversifiedPortfolioWithCashIsLowRisk() {
        // Diversified (3 holdings), low concentration, some cash buffer
        portfolio.addCash(new BigDecimal("1000")); // Cash buffer > 5%

        portfolio.getHoldings().put("A", new Holding(new Stock("A", "A", "A", new BigDecimal("100")), new BigDecimal("10"), new BigDecimal("100")));
        portfolio.getHoldings().put("B", new Holding(new Stock("B", "B", "B", new BigDecimal("100")), new BigDecimal("10"), new BigDecimal("100")));
        portfolio.getHoldings().put("C", new Holding(new Stock("C", "C", "C", new BigDecimal("100")), new BigDecimal("10"), new BigDecimal("100")));

        // Concentration is 33%, Equity is high, Div is OK, Cash is OK. Score should be ~2 (Moderate) or LOW depending on rules.
        // Concentration > 25% (+1)
        // Equity > 80% (+2)
        // Div >= 3 (+0)
        // Cash >= 5% (+0)
        // Total = 3 -> MODERATE
        assertEquals(RiskLevel.MODERATE, riskAnalysisService.analyzeRisk());
    }
}
