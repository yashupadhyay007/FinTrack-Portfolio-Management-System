package com.fintrack.service;

import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;

public class AnalyticsService {
    private final Portfolio portfolio;

    public AnalyticsService(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public BigDecimal getTotalInvestedAmount() {
        return portfolio.getHoldings().values().stream()
                .map(Holding::getInvestedAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getCurrentPortfolioValue() {
        return portfolio.getHoldings().values().stream()
                .map(Holding::getCurrentValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

        public BigDecimal getRealizedProfitLoss() {
        BigDecimal realizedProfit = BigDecimal.ZERO;
        java.util.Map<String, BigDecimal> qtyMap = new java.util.HashMap<>();
        java.util.Map<String, BigDecimal> costMap = new java.util.HashMap<>();
        
        for (com.fintrack.model.Transaction tx : portfolio.getTransactions()) {
            if (tx.getType() == com.fintrack.enums.TransactionType.BUY) {
                BigDecimal oldQty = qtyMap.getOrDefault(tx.getAssetId(), BigDecimal.ZERO);
                BigDecimal oldCost = costMap.getOrDefault(tx.getAssetId(), BigDecimal.ZERO);
                qtyMap.put(tx.getAssetId(), oldQty.add(tx.getQuantity()));
                costMap.put(tx.getAssetId(), oldCost.add(tx.getAmount()));
            } else if (tx.getType() == com.fintrack.enums.TransactionType.SELL) {
                BigDecimal oldQty = qtyMap.getOrDefault(tx.getAssetId(), BigDecimal.ZERO);
                BigDecimal oldCost = costMap.getOrDefault(tx.getAssetId(), BigDecimal.ZERO);
                if (oldQty.compareTo(BigDecimal.ZERO) > 0) {
                    BigDecimal avgCost = oldCost.divide(oldQty, 4, java.math.RoundingMode.HALF_UP);
                    BigDecimal costBasis = tx.getQuantity().multiply(avgCost);
                    BigDecimal profit = tx.getAmount().subtract(costBasis);
                    realizedProfit = realizedProfit.add(profit);
                    
                    qtyMap.put(tx.getAssetId(), oldQty.subtract(tx.getQuantity()));
                    costMap.put(tx.getAssetId(), oldCost.subtract(costBasis));
                }
            }
        }
        return realizedProfit;
    }

    public BigDecimal getTotalProfitLoss() {
        BigDecimal unrealized = getCurrentPortfolioValue().subtract(getTotalInvestedAmount());
        return unrealized.add(getRealizedProfitLoss());
    }

    public BigDecimal getReturnPercentage() {
        BigDecimal invested = getTotalInvestedAmount();
        if (invested.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return getTotalProfitLoss().divide(invested, 4, RoundingMode.HALF_UP);
    }

    public int getNumberOfHoldings() {
        return portfolio.getHoldings().size();
    }

    public Holding getLargestHolding() {
        Optional<Holding> max = portfolio.getHoldings().values().stream()
                .max(Comparator.comparing(Holding::getCurrentValue));
        return max.orElse(null);
    }

    public BigDecimal getAssetAllocation(String assetId) {
        Holding holding = portfolio.getHoldings().get(assetId);
        if (holding == null) return BigDecimal.ZERO;
        
        BigDecimal totalValue = getCurrentPortfolioValue();
        if (totalValue.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return holding.getCurrentValue().divide(totalValue, 4, RoundingMode.HALF_UP);
    }

    public BigDecimal getPortfolioConcentration() {
        Holding largest = getLargestHolding();
        if (largest == null) return BigDecimal.ZERO;
        
        BigDecimal totalValue = getCurrentPortfolioValue();
        if (totalValue.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;

        return largest.getCurrentValue().divide(totalValue, 4, RoundingMode.HALF_UP);
    }
}

