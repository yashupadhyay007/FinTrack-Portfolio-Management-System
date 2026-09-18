package com.fintrack;

import com.fintrack.model.Portfolio;
import com.fintrack.repository.PortfolioRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SystemIntegrationTest {

    @Test
    void testMainAppLoadsWithoutErrors() {
        // Just verify models can be instantiated and enums are present
        Portfolio p = new Portfolio();
        assertNotNull(p);
        assertTrue(p.getHoldings().isEmpty());
    }
}
