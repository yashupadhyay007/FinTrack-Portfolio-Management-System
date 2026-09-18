package com.fintrack.repository;

import com.fintrack.model.Portfolio;

public interface PortfolioRepository {
    Portfolio load();
    void save(Portfolio portfolio);
}
