package com.fintrack.service;

import com.fintrack.util.ConsoleFormatter;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class GoalPlannerService {

    public void calculateGoalRequirement(BigDecimal goalAmount, BigDecimal currentSavings, BigDecimal expectedAnnualReturn, int years) {
        System.out.println("\nFINANCIAL GOAL PLANNER RESULTS");
        System.out.println("==============================");
        System.out.println("Target Goal Amount: " + ConsoleFormatter.formatCurrency(goalAmount));
        System.out.println("Current Savings: " + ConsoleFormatter.formatCurrency(currentSavings));
        System.out.println("Expected Annual Return: " + ConsoleFormatter.formatPercent(expectedAnnualReturn));
        System.out.println("Time Horizon: " + years + " years\n");

        if (currentSavings.compareTo(goalAmount) >= 0) {
            System.out.println("Congratulations! You have already achieved your goal.");
            return;
        }

        if (years <= 0) {
            System.out.println("Time horizon must be at least 1 year.");
            return;
        }

        int months = years * 12;
        BigDecimal requiredMonthlyContribution;

        if (expectedAnnualReturn.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal remaining = goalAmount.subtract(currentSavings);
            requiredMonthlyContribution = remaining.divide(new BigDecimal(months), 2, RoundingMode.HALF_UP);
        } else {
            BigDecimal monthlyRate = expectedAnnualReturn.divide(new BigDecimal("12"), 6, RoundingMode.HALF_UP);
            
            // Future value of current savings: PV * (1 + r)^n
            BigDecimal futureValueOfSavings = currentSavings.multiply(BigDecimal.ONE.add(monthlyRate).pow(months));
            
            // Amount still needed
            BigDecimal shortfall = goalAmount.subtract(futureValueOfSavings);
            
            if (shortfall.compareTo(BigDecimal.ZERO) <= 0) {
                System.out.println("Your current savings will grow to exceed your goal without further contributions!");
                return;
            }

            // PMT formula: PMT = (FV * r) / ((1 + r)^n - 1)
            BigDecimal numerator = shortfall.multiply(monthlyRate);
            BigDecimal denominator = BigDecimal.ONE.add(monthlyRate).pow(months).subtract(BigDecimal.ONE);
            
            requiredMonthlyContribution = numerator.divide(denominator, 2, RoundingMode.HALF_UP);
        }

        System.out.println("Required Monthly Contribution: " + ConsoleFormatter.formatCurrency(requiredMonthlyContribution));
        System.out.println("Total Out of Pocket (Savings + Contributions): " + 
                ConsoleFormatter.formatCurrency(currentSavings.add(requiredMonthlyContribution.multiply(new BigDecimal(months)))));
    }
}
