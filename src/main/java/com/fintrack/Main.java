package com.fintrack;

import com.fintrack.cli.DataMenu;
import com.fintrack.cli.MainMenu;
import com.fintrack.model.Portfolio;
import com.fintrack.repository.FilePortfolioRepository;
import com.fintrack.repository.PortfolioRepository;
import com.fintrack.service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing FinTrack...");
        
        PortfolioRepository repository = new FilePortfolioRepository();
        Portfolio portfolio = repository.load();
        
        PortfolioService portfolioService = new PortfolioService(portfolio, repository);
        TransactionService transactionService = new TransactionService(portfolio, repository);
        AnalyticsService analyticsService = new AnalyticsService(portfolio);
        RiskAnalysisService riskAnalysisService = new RiskAnalysisService(portfolio, analyticsService);
        ReportService reportService = new ReportService(portfolio, analyticsService, riskAnalysisService);
        SimulationService simulationService = new SimulationService();
        GoalPlannerService goalPlannerService = new GoalPlannerService();
        
        Scanner scanner = new Scanner(System.in);
        DataMenu dataMenu = new DataMenu(scanner, portfolioService, transactionService);
        
        MainMenu mainMenu = new MainMenu(
            scanner, 
            portfolioService, 
            transactionService, 
            analyticsService, 
            riskAnalysisService, 
            reportService, 
            simulationService, 
            goalPlannerService, 
            dataMenu
        );

        mainMenu.show();
        scanner.close();
    }
}
