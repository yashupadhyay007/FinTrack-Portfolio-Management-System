package com.fintrack.model;

import com.fintrack.enums.AssetType;
import java.math.BigDecimal;

public class ETF extends Asset {
    public ETF(String id, String symbol, String name, BigDecimal currentPrice) {
        super(id, symbol, name, AssetType.ETF, currentPrice);
    }
}
