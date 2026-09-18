package com.fintrack.cli;

import com.fintrack.service.RiskAnalysisService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.util.Scanner;

public class RiskMenu extends Menu {
    private final RiskAnalysisService riskAnalysisService;

    public RiskMenu(Scanner scanner, RiskAnalysisService riskAnalysisService) {
        super(scanner);
        this.riskAnalysisService = riskAnalysisService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("RISK ANALYSIS");
            System.out.println("1. Run Risk Analysis");
            System.out.println("2. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            switch (choice) {
                case 1:
                    System.out.println("\n" + riskAnalysisService.generateRiskReport());
                    break;
                case 2:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
