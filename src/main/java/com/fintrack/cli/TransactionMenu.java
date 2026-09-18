package com.fintrack.cli;

import com.fintrack.enums.AssetType;
import com.fintrack.model.*;
import com.fintrack.service.TransactionService;
import com.fintrack.util.ConsoleFormatter;
import com.fintrack.util.InputValidator;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.UUID;

public class TransactionMenu extends Menu {
    private final TransactionService transactionService;

    public TransactionMenu(Scanner scanner, TransactionService transactionService) {
        super(scanner);
        this.transactionService = transactionService;
    }

    @Override
    public void show() {
        boolean back = false;
        while (!back) {
            ConsoleFormatter.printHeader("TRANSACTION MANAGEMENT");
            System.out.println("1. Deposit Cash");
            System.out.println("2. Withdraw Cash");
            System.out.println("3. Buy Asset");
            System.out.println("4. Sell Asset");
            System.out.println("5. Back to Main Menu");

            int choice = InputValidator.readInt(scanner, "Enter choice: ");
            try {
                switch (choice) {
                    case 1: deposit(); break;
                    case 2: withdraw(); break;
                    case 3: buyAsset(); break;
                    case 4: sellAsset(); break;
                    case 5: back = true; break;
                    default: System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("Transaction Error: " + e.getMessage());
            }
        }
    }

    private void deposit() {
        BigDecimal amount = InputValidator.readPositiveBigDecimal(scanner, "Enter amount to deposit: ");
        String notes = InputValidator.readString(scanner, "Enter notes (optional): ");
        transactionService.deposit(amount, notes);
        System.out.println("Deposit successful.");
    }

    private void withdraw() {
        BigDecimal amount = InputValidator.readPositiveBigDecimal(scanner, "Enter amount to withdraw: ");
        String notes = InputValidator.readString(scanner, "Enter notes (optional): ");
        transactionService.withdraw(amount, notes);
        System.out.println("Withdrawal successful.");
    }

    private void buyAsset() {
        System.out.println("\n--- Buy Asset ---");
        String symbol = InputValidator.readStringRequired(scanner, "Enter Asset Symbol (e.g., AAPL): ").toUpperCase();
        String name = InputValidator.readStringRequired(scanner, "Enter Asset Name (e.g., Apple Inc): ");
        
        System.out.println("Asset Types: 1. STOCK  2. MUTUAL_FUND  3. ETF  4. GOLD");
        int typeChoice = InputValidator.readInt(scanner, "Select type (1-4): ");
        AssetType type;
        switch (typeChoice) {
            case 1: type = AssetType.STOCK; break;
            case 2: type = AssetType.MUTUAL_FUND; break;
            case 3: type = AssetType.ETF; break;
            case 4: type = AssetType.GOLD; break;
            default: System.out.println("Invalid type."); return;
        }

        BigDecimal price = InputValidator.readPositiveBigDecimal(scanner, "Enter price per unit: ");
        BigDecimal quantity = InputValidator.readPositiveBigDecimal(scanner, "Enter quantity: ");
        String notes = InputValidator.readString(scanner, "Enter notes (optional): ");

        // We use symbol as ID for simplicity in this academic project
        Asset asset;
        switch (type) {
            case STOCK: asset = new Stock(symbol, symbol, name, price); break;
            case MUTUAL_FUND: asset = new MutualFund(symbol, symbol, name, price); break;
            case ETF: asset = new ETF(symbol, symbol, name, price); break;
            case GOLD: asset = new Gold(symbol, symbol, name, price); break;
            default: throw new IllegalStateException();
        }

        transactionService.buy(asset, quantity, price, notes);
        System.out.println("Purchase successful.");
    }

    private void sellAsset() {
        System.out.println("\n--- Sell Asset ---");
        String assetId = InputValidator.readStringRequired(scanner, "Enter Asset ID/Symbol to sell: ").toUpperCase();
        BigDecimal quantity = InputValidator.readPositiveBigDecimal(scanner, "Enter quantity to sell: ");
        BigDecimal price = InputValidator.readPositiveBigDecimal(scanner, "Enter selling price per unit: ");
        String notes = InputValidator.readString(scanner, "Enter notes (optional): ");

        transactionService.sell(assetId, quantity, price, notes);
        System.out.println("Sale successful.");
    }
}
