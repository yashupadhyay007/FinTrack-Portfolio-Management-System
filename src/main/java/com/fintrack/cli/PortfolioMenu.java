package com.fintrack.cli;

import com.fintrack.model.Holding;
import com.fintrack.service.AnalyticsService;
import com.fintrack.service.PortfolioService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class PortfolioMenu extends Menu {
    private final PortfolioService portfolioService;
    private final AnalyticsService analyticsService;

    public PortfolioMenu(Scanner scanner, PortfolioService portfolioService, AnalyticsService analyticsService) {
        super(scanner);
        this.portfolioService = portfolioService;
        this.analyticsService = analyticsService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("PORTFOLIO MANAGEMENT");
            System.out.println("1. View All Holdings");
            System.out.println("2. Update Asset Price");
            System.out.println("3. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1:
                    viewHoldings();
                    break;
                case 2:
                    updateAssetPrice();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewHoldings() {
        ConsoleFormatter.printHeader("CURRENT HOLDINGS");
        if (portfolioService.getAllHoldings().isEmpty()) {
            System.out.println("No assets in portfolio.");
            return;
        }

        System.out.printf("%-10s %-20s %-10s %-15s %-15s %-15s\n", "Symbol", "Name", "Qty", "Avg Price", "Cur Price", "Value");
        ConsoleFormatter.printSeparator();
        
        for (Holding h : portfolioService.getAllHoldings().values()) {
            System.out.printf("%-10s %-20.20s %-10.2f %-15s %-15s %-15s\n",
                    h.getAsset().getSymbol(),
                    h.getAsset().getName(),
                    h.getQuantity(),
                    ConsoleFormatter.formatCurrency(h.getAveragePurchasePrice()),
                    ConsoleFormatter.formatCurrency(h.getAsset().getCurrentPrice()),
                    ConsoleFormatter.formatCurrency(h.getCurrentValue()));
        }
    }

    private void updateAssetPrice() {
        String assetId = InputValidator.readStringRequired(scanner, "Enter Asset ID/Symbol to update: ");
        if (!portfolioService.getAllHoldings().containsKey(assetId)) {
            System.out.println("Asset not found in portfolio. (Note: use exact ID used when buying)");
            return;
        }
        
        BigDecimal newPrice = InputValidator.readNonNegativeBigDecimal(scanner, "Enter new price: ");
        portfolioService.updateAssetPrice(assetId, newPrice);
        System.out.println("Price updated successfully.");
    }
}
