package com.fintrack.cli;

import com.fintrack.service.GoalPlannerService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;

public class GoalPlannerMenu extends Menu {
    private final GoalPlannerService goalPlannerService;

    public GoalPlannerMenu(Scanner scanner, GoalPlannerService goalPlannerService) {
        super(scanner);
        this.goalPlannerService = goalPlannerService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("FINANCIAL GOAL PLANNER");
            System.out.println("1. Calculate Goal Requirements");
            System.out.println("2. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            if (choice == 1) {
                calculateGoal();
            } else if (choice == 2) {
                back = true;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    private void calculateGoal() {
        BigDecimal goal = InputValidator.readPositiveBigDecimal(scanner, "Enter Target Goal Amount (e.g., 1000000): ");
        BigDecimal current = InputValidator.readNonNegativeBigDecimal(scanner, "Enter Current Savings/Investment: ");
        BigDecimal ratePercent = InputValidator.readNonNegativeBigDecimal(scanner, "Enter Expected Annual Return % (e.g., 12): ");
        int years = InputValidator.readInt(scanner, "Enter Time Horizon in Years: ");

        BigDecimal rate = ratePercent.divide(new BigDecimal("100"));
        goalPlannerService.calculateGoalRequirement(goal, current, rate, years);
    }
}
