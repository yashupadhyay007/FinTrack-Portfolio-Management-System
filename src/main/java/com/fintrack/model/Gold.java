package com.fintrack.model;

import com.fintrack.enums.AssetType;
import java.math.BigDecimal;

public class Gold extends Asset {
    public Gold(String id, String symbol, String name, BigDecimal currentPrice) {
        super(id, symbol, name, AssetType.GOLD, currentPrice);
    }
}
