package com.fintrack.service;

import com.fintrack.enums.AssetType;
import com.fintrack.enums.RiskLevel;
import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class RiskAnalysisService {
    private final Portfolio portfolio;
    private final AnalyticsService analyticsService;

    public RiskAnalysisService(Portfolio portfolio, AnalyticsService analyticsService) {
        this.portfolio = portfolio;
        this.analyticsService = analyticsService;
    }

    public RiskLevel analyzeRisk() {
        if (portfolio.getHoldings().isEmpty()) {
            return RiskLevel.LOW;
        }

        BigDecimal concentration = analyticsService.getPortfolioConcentration();
        BigDecimal cashRatio = getCashRatio();
        BigDecimal equityRatio = getAssetClassAllocation(AssetType.STOCK).add(getAssetClassAllocation(AssetType.MUTUAL_FUND));

        int riskScore = 0;

        // Rule 1: Concentration
        if (concentration.compareTo(new BigDecimal("0.50")) > 0) {
            riskScore += 2;
        } else if (concentration.compareTo(new BigDecimal("0.25")) > 0) {
            riskScore += 1;
        }

        // Rule 2: Equity Exposure
        if (equityRatio.compareTo(new BigDecimal("0.80")) > 0) {
            riskScore += 2;
        } else if (equityRatio.compareTo(new BigDecimal("0.50")) > 0) {
            riskScore += 1;
        }

        // Rule 3: Diversification (number of holdings)
        if (analyticsService.getNumberOfHoldings() < 3) {
            riskScore += 1;
        }

        // Rule 4: Cash Buffer
        if (cashRatio.compareTo(new BigDecimal("0.05")) < 0) {
            riskScore += 1;
        }

        if (riskScore >= 4) {
            return RiskLevel.HIGH;
        } else if (riskScore >= 2) {
            return RiskLevel.MODERATE;
        } else {
            return RiskLevel.LOW;
        }
    }

    public String generateRiskReport() {
        StringBuilder report = new StringBuilder();
        report.append("RISK ANALYSIS REPORT\n");
        report.append("====================\n\n");
        report.append("Disclaimer: This is an educational rule-based analysis, not financial advice.\n\n");

        report.append("Overall Risk Level: ").append(analyzeRisk()).append("\n\n");

        report.append("Metrics Evaluated:\n");
        report.append("- Largest Holding Concentration: ").append(analyticsService.getPortfolioConcentration().multiply(new BigDecimal("100"))).append("%\n");
        
        BigDecimal equityRatio = getAssetClassAllocation(AssetType.STOCK).add(getAssetClassAllocation(AssetType.MUTUAL_FUND));
        report.append("- Equity/Fund Exposure: ").append(equityRatio.multiply(new BigDecimal("100"))).append("%\n");
        report.append("- Number of Holdings: ").append(analyticsService.getNumberOfHoldings()).append("\n");
        report.append("- Cash Buffer Ratio: ").append(getCashRatio().multiply(new BigDecimal("100"))).append("%\n");
        
        return report.toString();
    }

    private BigDecimal getCashRatio() {
        BigDecimal totalAssetValue = analyticsService.getCurrentPortfolioValue();
        BigDecimal totalPortfolioValue = totalAssetValue.add(portfolio.getCashBalance());
        if (totalPortfolioValue.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        
        return portfolio.getCashBalance().divide(totalPortfolioValue, 4, RoundingMode.HALF_UP);
    }

    private BigDecimal getAssetClassAllocation(AssetType type) {
        BigDecimal classValue = portfolio.getHoldings().values().stream()
                .filter(h -> h.getAsset().getType() == type)
                .map(Holding::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        BigDecimal totalValue = analyticsService.getCurrentPortfolioValue();
        if (totalValue.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return classValue.divide(totalValue, 4, RoundingMode.HALF_UP);
    }
}
