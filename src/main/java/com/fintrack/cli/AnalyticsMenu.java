package com.fintrack.cli;

import com.fintrack.service.AnalyticsService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.util.Scanner;

public class AnalyticsMenu extends Menu {
    private final AnalyticsService analyticsService;

    public AnalyticsMenu(Scanner scanner, AnalyticsService analyticsService) {
        super(scanner);
        this.analyticsService = analyticsService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("PORTFOLIO ANALYTICS");
            System.out.println("1. View Overall Performance");
            System.out.println("2. View Asset Allocation");
            System.out.println("3. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1:
                    viewPerformance();
                    break;
                case 2:
                    viewAllocation();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void viewPerformance() {
        System.out.println("\nOVERALL PERFORMANCE");
        System.out.println("-------------------");
        System.out.println("Total Invested   : " + ConsoleFormatter.formatCurrency(analyticsService.getTotalInvestedAmount()));
        System.out.println("Current Value    : " + ConsoleFormatter.formatCurrency(analyticsService.getCurrentPortfolioValue()));
        System.out.println("Total Profit/Loss: " + ConsoleFormatter.formatCurrency(analyticsService.getTotalProfitLoss()));
        System.out.println("Return Percentage: " + ConsoleFormatter.formatPercent(analyticsService.getReturnPercentage()));
    }

    private void viewAllocation() {
        System.out.println("\nASSET ALLOCATION");
        System.out.println("----------------");
        // We could implement asset-wise allocation fetching logic here or just rely on the report.
        System.out.println("Portfolio Concentration (Largest Holding): " + 
                ConsoleFormatter.formatPercent(analyticsService.getPortfolioConcentration()));
        if (analyticsService.getLargestHolding() != null) {
            System.out.println("Largest Holding: " + analyticsService.getLargestHolding().getAsset().getName());
        }
    }
}
