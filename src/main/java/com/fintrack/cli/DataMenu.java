package com.fintrack.cli;

import com.fintrack.model.Stock;
import com.fintrack.model.MutualFund;
import com.fintrack.service.PortfolioService;
import com.fintrack.service.TransactionService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class DataMenu extends Menu {
    private final PortfolioService portfolioService;
    private final TransactionService transactionService;

    public DataMenu(Scanner scanner, PortfolioService portfolioService, TransactionService transactionService) {
        super(scanner);
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("DATA MANAGEMENT");
            System.out.println("1. Save Portfolio Now");
            System.out.println("2. Load Demo/Sample Data");
            System.out.println("3. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1:
                    portfolioService.savePortfolio();
                    System.out.println("Portfolio saved successfully.");
                    break;
                case 2:
                    loadDemoData();
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void loadDemoData() {
        System.out.println("\nWARNING: This will deposit demo cash and buy demo assets into your current portfolio.");
        String confirm = InputValidator.readStringRequired(scanner, "Proceed? (y/n): ");
        if (confirm.equalsIgnoreCase("y")) {
            transactionService.deposit(new BigDecimal("100000"), "Demo Initial Deposit");
            transactionService.buy(new Stock("AAPL", "AAPL", "Apple Inc", new BigDecimal("150.00")), new BigDecimal("100"), new BigDecimal("150.00"), "Demo Stock Purchase");
            transactionService.buy(new MutualFund("HDFCTOP100", "HDFCTOP100", "HDFC Top 100 Fund", new BigDecimal("500.00")), new BigDecimal("50"), new BigDecimal("500.00"), "Demo MF Purchase");
            portfolioService.savePortfolio();
            System.out.println("Demo data loaded successfully.");
        } else {
            System.out.println("Demo data load cancelled.");
        }
    }
}
