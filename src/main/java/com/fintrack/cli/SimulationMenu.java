package com.fintrack.cli;

import com.fintrack.service.SimulationService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class SimulationMenu extends Menu {
    private final SimulationService simulationService;

    public SimulationMenu(Scanner scanner, SimulationService simulationService) {
        super(scanner);
        this.simulationService = simulationService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("INVESTMENT SIMULATOR");
            System.out.println("1. Run Future Value Simulation");
            System.out.println("2. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            if (choice == 1) {
                runSimulation();
            } else if (choice == 2) {
                back = true;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void runSimulation() {
        BigDecimal initial = InputValidator.readNonNegativeBigDecimal(scanner, "Enter Initial Investment (e.g., 10000): ");
        BigDecimal monthly = InputValidator.readNonNegativeBigDecimal(scanner, "Enter Monthly Contribution (e.g., 500): ");
        BigDecimal ratePercent = InputValidator.readNonNegativeBigDecimal(scanner, "Enter Expected Annual Return % (e.g., 12.5): ");
        int years = InputValidator.readInt(scanner, "Enter Investment Period in Years (e.g., 10): ");

        BigDecimal rate = ratePercent.divide(new BigDecimal("100"));
        simulationService.runSimulation(initial, monthly, rate, years);
    }
}
