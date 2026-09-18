package com.fintrack.service;

import com.fintrack.util.ConsoleFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class SimulationService {
    
    public void runSimulation(BigDecimal initialInvestment, BigDecimal monthlyContribution, BigDecimal expectedAnnualReturn, int years) {
        System.out.println("\nINVESTMENT SIMULATION RESULTS");
        System.out.println("=============================");
        System.out.println("Initial Investment: " + ConsoleFormatter.formatCurrency(initialInvestment));
        System.out.println("Monthly Contribution: " + ConsoleFormatter.formatCurrency(monthlyContribution));
        System.out.println("Expected Annual Return: " + ConsoleFormatter.formatPercent(expectedAnnualReturn));
        System.out.println("Investment Period: " + years + " years\n");

        BigDecimal monthlyRate = expectedAnnualReturn.divide(new BigDecimal("12"), 6, RoundingMode.HALF_UP);
        BigDecimal currentBalance = initialInvestment;
        BigDecimal totalContributions = initialInvestment;

        System.out.printf("%-5s | %-20s | %-20s | %-20s\n", "Year", "Total Contributions", "Estimated Growth", "Estimated Value");
        System.out.println("-".repeat(75));

        for (int year = 1; year <= years; year++) {
            for (int month = 1; month <= 12; month++) {
                currentBalance = currentBalance.add(monthlyContribution);
                currentBalance = currentBalance.multiply(BigDecimal.ONE.add(monthlyRate));
                totalContributions = totalContributions.add(monthlyContribution);
            }
            
            BigDecimal estimatedGrowth = currentBalance.subtract(totalContributions);
            
            System.out.printf("%-5d | %-20s | %-20s | %-20s\n", 
                    year, 
                    ConsoleFormatter.formatCurrency(totalContributions), 
                    ConsoleFormatter.formatCurrency(estimatedGrowth), 
                    ConsoleFormatter.formatCurrency(currentBalance));
        }
        
        System.out.println("\nDisclaimer: This is a mathematical projection. Returns are not guaranteed.");
    }
}
