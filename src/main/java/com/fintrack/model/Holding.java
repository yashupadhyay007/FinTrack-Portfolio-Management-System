package com.fintrack.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Holding {
    private Asset asset;
    private BigDecimal quantity;
    private BigDecimal averagePurchasePrice;
    private BigDecimal investedAmount;

    public Holding(Asset asset, BigDecimal quantity, BigDecimal averagePurchasePrice) {
        this.asset = asset;
        this.quantity = quantity;
        this.averagePurchasePrice = averagePurchasePrice;
        this.investedAmount = quantity.multiply(averagePurchasePrice);
    }

    public Asset getAsset() { return asset; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getAveragePurchasePrice() { return averagePurchasePrice; }
    public BigDecimal getInvestedAmount() { return investedAmount; }

    public void addQuantity(BigDecimal addedQuantity, BigDecimal price) {
        BigDecimal newInvestedAmount = this.investedAmount.add(addedQuantity.multiply(price));
        this.quantity = this.quantity.add(addedQuantity);
        
        if (this.quantity.compareTo(BigDecimal.ZERO) > 0) {
            this.averagePurchasePrice = newInvestedAmount.divide(this.quantity, 4, RoundingMode.HALF_UP);
        } else {
            this.averagePurchasePrice = BigDecimal.ZERO;
        }
        this.investedAmount = newInvestedAmount;
    }

    public void removeQuantity(BigDecimal removedQuantity) {
        this.quantity = this.quantity.subtract(removedQuantity);
        this.investedAmount = this.quantity.multiply(this.averagePurchasePrice);
    }

    public BigDecimal getCurrentValue() {
        return quantity.multiply(asset.getCurrentPrice());
    }

    public BigDecimal getProfitLoss() {
        return getCurrentValue().subtract(investedAmount);
    }

    public String toCsv() {
        return String.join(",", 
            asset.getId(), 
            quantity.toString(), 
            averagePurchasePrice.toString()
        );
    }
}
