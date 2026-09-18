package com.fintrack.service;

import com.fintrack.enums.RiskLevel;
import com.fintrack.model.Holding;
import com.fintrack.model.Portfolio;
import com.fintrack.model.Transaction;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.DateUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

public class ReportService {
    private final Portfolio portfolio;
    private final AnalyticsService analyticsService;
    private final RiskAnalysisService riskAnalysisService;

    public ReportService(Portfolio portfolio, AnalyticsService analyticsService, RiskAnalysisService riskAnalysisService) {
        this.portfolio = portfolio;
        this.analyticsService = analyticsService;
        this.riskAnalysisService = riskAnalysisService;
    }

    public String generatePortfolioSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("PORTFOLIO SUMMARY REPORT\n");
        sb.append("========================\n");
        sb.append("Generated on: ").append(DateUtil.format(LocalDateTime.now())).append("\n\n");
        
        sb.append(String.format("%-25s: %s\n", "Cash Balance", ConsoleFormatter.formatCurrency(portfolio.getCashBalance())));
        sb.append(String.format("%-25s: %s\n", "Invested Amount", ConsoleFormatter.formatCurrency(analyticsService.getTotalInvestedAmount())));
        sb.append(String.format("%-25s: %s\n", "Current Value", ConsoleFormatter.formatCurrency(analyticsService.getCurrentPortfolioValue())));
        sb.append(String.format("%-25s: %s\n", "Profit/Loss", ConsoleFormatter.formatCurrency(analyticsService.getTotalProfitLoss())));
        sb.append(String.format("%-25s: %s\n", "Overall Return", ConsoleFormatter.formatPercent(analyticsService.getReturnPercentage())));
        sb.append(String.format("%-25s: %d\n", "Total Holdings", analyticsService.getNumberOfHoldings()));
        
        RiskLevel riskLevel = riskAnalysisService.analyzeRisk();
        sb.append(String.format("%-25s: %s\n", "Assessed Risk Level", riskLevel));
        
        return sb.toString();
    }

    public String generateHoldingsReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("HOLDINGS REPORT\n");
        sb.append("===============\n\n");
        
        sb.append(String.format("%-15s %-20s %-10s %-15s %-15s %-15s %-15s %-10s\n", 
                "Symbol", "Name", "Qty", "Avg Price", "Cur Price", "Invested", "Cur Value", "Return"));
        sb.append("-".repeat(125)).append("\n");
        
        portfolio.getHoldings().values().stream()
                .sorted(Comparator.comparing(h -> h.getAsset().getSymbol()))
                .forEach(h -> {
                    BigDecimal ret = h.getInvestedAmount().compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO 
                            : h.getProfitLoss().divide(h.getInvestedAmount(), 4, BigDecimal.ROUND_HALF_UP);
                    
                    sb.append(String.format("%-15s %-20.20s %-10.2f %-15s %-15s %-15s %-15s %-10s\n",
                            h.getAsset().getSymbol(),
                            h.getAsset().getName(),
                            h.getQuantity(),
                            ConsoleFormatter.formatCurrency(h.getAveragePurchasePrice()),
                            ConsoleFormatter.formatCurrency(h.getAsset().getCurrentPrice()),
                            ConsoleFormatter.formatCurrency(h.getInvestedAmount()),
                            ConsoleFormatter.formatCurrency(h.getCurrentValue()),
                            ConsoleFormatter.formatPercent(ret)));
                });
                
        return sb.toString();
    }

    public String generateTransactionHistory() {
        StringBuilder sb = new StringBuilder();
        sb.append("TRANSACTION HISTORY\n");
        sb.append("===================\n\n");
        
        sb.append(String.format("%-20s %-10s %-10s %-10s %-15s %-15s %s\n", 
                "Date", "Type", "Asset", "Qty", "Price", "Amount", "Notes"));
        sb.append("-".repeat(100)).append("\n");
        
        List<Transaction> txs = portfolio.getTransactions();
        for (Transaction tx : txs) {
            sb.append(String.format("%-20s %-10s %-10s %-10.2f %-15s %-15s %s\n",
                    DateUtil.format(tx.getTimestamp()),
                    tx.getType().name(),
                    tx.getAssetId() != null ? tx.getAssetId() : "-",
                    tx.getQuantity() != null ? tx.getQuantity() : BigDecimal.ZERO,
                    tx.getPrice() != null ? ConsoleFormatter.formatCurrency(tx.getPrice()) : "-",
                    ConsoleFormatter.formatCurrency(tx.getAmount()),
                    tx.getNotes() != null ? tx.getNotes() : ""));
        }
        
        return sb.toString();
    }

    public void exportReport(String reportContent, String filename) {
        String dir = "data/reports";
        try {
            java.nio.file.Files.createDirectories(java.nio.file.Paths.get(dir));
            String path = dir + "/" + filename + "_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
            try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
                writer.print(reportContent);
                System.out.println("Report successfully exported to: " + path);
            }
        } catch (IOException e) {
            System.err.println("Failed to export report: " + e.getMessage());
        }
    }
}
