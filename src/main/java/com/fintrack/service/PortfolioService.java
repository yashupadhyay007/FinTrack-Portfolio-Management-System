package com.fintrack.service;

import com.fintrack.exception.AssetNotFoundException;
import com.fintrack.model.Asset;
import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;
import com.fintrack.repository.PortfolioRepository;
import java.math.BigDecimal;
import java.util.Map;

public class PortfolioService {
    private final Portfolio portfolio;
    private final PortfolioRepository repository;

    public PortfolioService(Portfolio portfolio, PortfolioRepository repository) {
        this.portfolio = portfolio;
        this.repository = repository;
    }
    
    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void updateAssetPrice(String assetId, BigDecimal newPrice) {
        Holding holding = portfolio.getHoldings().get(assetId);
        if (holding == null) {
            throw new AssetNotFoundException("Asset with ID " + assetId + " not found in portfolio.");
        }
        holding.getAsset().setCurrentPrice(newPrice);
        repository.save(portfolio);
    }

    public Map<String, Holding> getAllHoldings() {
        return portfolio.getHoldings();
    }
    
    public void addCash(BigDecimal amount) {
        portfolio.addCash(amount);
        repository.save(portfolio);
    }
    
    public void subtractCash(BigDecimal amount) {
        portfolio.subtractCash(amount);
        repository.save(portfolio);
    }
    
    public void savePortfolio() {
        repository.save(portfolio);
    }
}
