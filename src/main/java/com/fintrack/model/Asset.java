package com.fintrack.model;

import com.fintrack.enums.AssetType;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * Represents a generic financial asset.
 */
public abstract class Asset {
    private String id;
    private String symbol;
    private String name;
    private AssetType type;
    private BigDecimal currentPrice;

    public Asset(String id, String symbol, String name, AssetType type, BigDecimal currentPrice) {
        this.id = id;
        this.symbol = symbol;
        this.name = name;
        this.type = type;
        this.currentPrice = currentPrice;
    }

    public String getId() { return id; }
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public AssetType getType() { return type; }
    
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public void setCurrentPrice(BigDecimal currentPrice) { this.currentPrice = currentPrice; }

    /**
     * Converts this asset to a standard CSV format for persistence.
     */
    public String toCsv() {
        return String.join(",", id, symbol, name, type.name(), currentPrice.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Asset asset = (Asset) o;
        return id.equals(asset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %s", name, symbol, type);
    }
}
