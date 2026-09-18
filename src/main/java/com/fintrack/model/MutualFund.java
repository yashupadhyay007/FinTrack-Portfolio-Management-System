package com.fintrack.model;

import com.fintrack.enums.AssetType;
import java.math.BigDecimal;

public class MutualFund extends Asset {
    public MutualFund(String id, String symbol, String name, BigDecimal currentPrice) {
        super(id, symbol, name, AssetType.MUTUAL_FUND, currentPrice);
    }
}
