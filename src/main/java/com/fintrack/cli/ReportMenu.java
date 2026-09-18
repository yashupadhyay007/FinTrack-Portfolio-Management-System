package com.fintrack.cli;

import com.fintrack.service.ReportService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.util.Scanner;

public class ReportMenu extends Menu {
    private final ReportService reportService;

    public ReportMenu(Scanner scanner, ReportService reportService) {
        super(scanner);
        this.reportService = reportService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("REPORTS");
            System.out.println("1. Portfolio Summary Report");
            System.out.println("2. Holdings Report");
            System.out.println("3. Transaction History Report");
            System.out.println("4. Export Report to File");
            System.out.println("5. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1:
                    System.out.println("\n" + reportService.generatePortfolioSummary());
                    break;
                case 2:
                    System.out.println("\n" + reportService.generateHoldingsReport());
                    break;
                case 3:
                    System.out.println("\n" + reportService.generateTransactionHistory());
                    break;
                case 4:
                    exportReport();
                    break;
                case 5:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void exportReport() {
        System.out.println("Which report to export?");
        System.out.println("1. Portfolio Summary");
        System.out.println("2. Holdings Report");
        System.out.println("3. Transaction History");
        int rChoice = InputValidator.readInt(scanner, "Enter choice: ");
        
        String content;
        String prefix;
        switch (rChoice) {
            case 1: 
                content = reportService.generatePortfolioSummary(); 
                prefix = "portfolio_summary";
                break;
            case 2: 
                content = reportService.generateHoldingsReport(); 
                prefix = "holdings_report";
                break;
            case 3: 
                content = reportService.generateTransactionHistory(); 
                prefix = "transaction_history";
                break;
            default: 
                System.out.println("Invalid choice."); 
                return;
        }
        
        reportService.exportReport(content, prefix);
    }
}
