package com.fintrack.model;

import com.fintrack.enums.AssetType;
import java.math.BigDecimal;

public class Stock extends Asset {
    public Stock(String id, String symbol, String name, BigDecimal currentPrice) {
        super(id, symbol, name, AssetType.STOCK, currentPrice);
    }
}
