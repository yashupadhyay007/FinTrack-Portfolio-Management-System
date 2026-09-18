package com.fintrack.cli;

import com.fintrack.service.*;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;
import java.util.Scanner;

public class MainMenu extends Menu {
    private final PortfolioService portfolioService;
    private final TransactionService transactionService;
    private final AnalyticsService analyticsService;
    private final RiskAnalysisService riskAnalysisService;
    private final ReportService reportService;
    private final SimulationService simulationService;
    private final GoalPlannerService goalPlannerService;
    private final DataMenu dataMenu;

    public MainMenu(Scanner scanner, PortfolioService portfolioService, TransactionService transactionService,
                    AnalyticsService analyticsService, RiskAnalysisService riskAnalysisService,
                    ReportService reportService, SimulationService simulationService,
                    GoalPlannerService goalPlannerService, DataMenu dataMenu) {
        super(scanner);
        this.portfolioService = portfolioService;
        this.transactionService = transactionService;
        this.analyticsService = analyticsService;
        this.riskAnalysisService = riskAnalysisService;
        this.reportService = reportService;
        this.simulationService = simulationService;
        this.goalPlannerService = goalPlannerService;
        this.dataMenu = dataMenu;
    }

    @Override
    public void show() {
        boolean exit = false;
        while (!exit) {
            printDashboard();
            System.out.println("\nMAIN MENU");
            System.out.println("1. Portfolio Management");
            System.out.println("2. Transaction Management");
            System.out.println("3. Portfolio Analytics");
            System.out.println("4. Risk Analysis");
            System.out.println("5. Reports");
            System.out.println("6. Investment Simulator");
            System.out.println("7. Financial Goal Planner");
            System.out.println("8. Data Management (Save/Demo)");
            System.out.println("9. Exit");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            
            try {
                switch (choice) {
                    case 1: new PortfolioMenu(scanner, portfolioService, analyticsService).show(); break;
                    case 2: new TransactionMenu(scanner, transactionService).show(); break;
                    case 3: new AnalyticsMenu(scanner, analyticsService).show(); break;
                    case 4: new RiskMenu(scanner, riskAnalysisService).show(); break;
                    case 5: new ReportMenu(scanner, reportService).show(); break;
                    case 6: new SimulationMenu(scanner, simulationService).show(); break;
                    case 7: new GoalPlannerMenu(scanner, goalPlannerService).show(); break;
                    case 8: dataMenu.show(); break;
                    case 9:
                        portfolioService.savePortfolio();
                        System.out.println("Data saved. Exiting FinTrack. Goodbye!");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }

    private void printDashboard() {
        ConsoleFormatter.printHeader("FINTRACK DASHBOARD");
        System.out.printf("%-20s: %s\n", "Cash Balance", ConsoleFormatter.formatCurrency(portfolioService.getPortfolio().getCashBalance()));
        System.out.printf("%-20s: %s\n", "Invested Amount", ConsoleFormatter.formatCurrency(analyticsService.getTotalInvestedAmount()));
        System.out.printf("%-20s: %s\n", "Current Value", ConsoleFormatter.formatCurrency(analyticsService.getCurrentPortfolioValue()));
        System.out.printf("%-20s: %s\n", "Profit/Loss", ConsoleFormatter.formatCurrency(analyticsService.getTotalProfitLoss()));
        System.out.printf("%-20s: %s\n", "Return", ConsoleFormatter.formatPercent(analyticsService.getReturnPercentage()));
        System.out.printf("%-20s: %d\n", "Holdings", analyticsService.getNumberOfHoldings());
        System.out.printf("%-20s: %s\n", "Risk Level", riskAnalysisService.analyzeRisk());
        ConsoleFormatter.printSeparator();
    }
}
