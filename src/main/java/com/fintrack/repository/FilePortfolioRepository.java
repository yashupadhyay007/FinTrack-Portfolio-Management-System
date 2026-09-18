package com.fintrack.repository;

import com.fintrack.enums.AssetType;
import com.fintrack.enums.TransactionType;
import com.fintrack.model.*;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class FilePortfolioRepository implements PortfolioRepository {
    private static final String DATA_DIR = "data";
    private static final String PORTFOLIO_FILE = DATA_DIR + "/portfolio.csv";
    private static final String HOLDINGS_FILE = DATA_DIR + "/holdings.csv";
    private static final String TRANSACTIONS_FILE = DATA_DIR + "/transactions.csv";
    private static final String ASSETS_FILE = DATA_DIR + "/assets.csv"; // Metadata about known assets

    public FilePortfolioRepository() {
        ensureDataDirectoryExists();
    }

    private void ensureDataDirectoryExists() {
        try {
            Files.createDirectories(Paths.get(DATA_DIR));
        } catch (IOException e) {
            System.err.println("Warning: Could not create data directory.");
        }
    }

    @Override
    public Portfolio load() {
        Portfolio portfolio = new Portfolio();
        Map<String, Asset> knownAssets = loadAssets();

        loadPortfolioMetadata(portfolio);
        loadHoldings(portfolio, knownAssets);
        loadTransactions(portfolio);

        return portfolio;
    }

    @Override
    public void save(Portfolio portfolio) {
        savePortfolioMetadata(portfolio);
        saveAssets(portfolio); // save metadata of assets in holdings/transactions
        saveHoldings(portfolio);
        saveTransactions(portfolio);
    }

    private Map<String, Asset> loadAssets() {
        Map<String, Asset> assets = new HashMap<>();
        File file = new File(ASSETS_FILE);
        if (!file.exists()) return assets;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String id = parts[0];
                    String symbol = parts[1];
                    String name = parts[2];
                    AssetType type = AssetType.valueOf(parts[3]);
                    BigDecimal price = new BigDecimal(parts[4]);

                    Asset asset = createAsset(id, symbol, name, type, price);
                    assets.put(id, asset);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading assets: " + e.getMessage());
        }
        return assets;
    }
    
    private Asset createAsset(String id, String symbol, String name, AssetType type, BigDecimal price) {
        switch (type) {
            case STOCK: return new Stock(id, symbol, name, price);
            case MUTUAL_FUND: return new MutualFund(id, symbol, name, price);
            case ETF: return new ETF(id, symbol, name, price);
            case GOLD: return new Gold(id, symbol, name, price);
            case INTERNATIONAL_FUND: return new MutualFund(id, symbol, name, price); // Fallback to MutualFund class
            default: throw new IllegalArgumentException("Unknown asset type");
        }
    }

    private void loadPortfolioMetadata(Portfolio portfolio) {
        File file = new File(PORTFOLIO_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null && !line.trim().isEmpty()) {
                portfolio.setCashBalance(new BigDecimal(line.trim()));
            }
        } catch (Exception e) {
            System.err.println("Error loading portfolio metadata: " + e.getMessage());
        }
    }

    private void loadHoldings(Portfolio portfolio, Map<String, Asset> knownAssets) {
        File file = new File(HOLDINGS_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String assetId = parts[0];
                    BigDecimal quantity = new BigDecimal(parts[1]);
                    BigDecimal avgPrice = new BigDecimal(parts[2]);
                    
                    Asset asset = knownAssets.get(assetId);
                    if (asset != null) {
                        Holding holding = new Holding(asset, quantity, avgPrice);
                        portfolio.getHoldings().put(assetId, holding);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading holdings: " + e.getMessage());
        }
    }

    private void loadTransactions(Portfolio portfolio) {
        File file = new File(TRANSACTIONS_FILE);
        if (!file.exists()) return;

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1); // Keep empty trailing fields
                if (parts.length >= 7) {
                    String id = parts[0];
                    TransactionType type = TransactionType.valueOf(parts[1]);
                    String assetId = parts[2].isEmpty() ? null : parts[2];
                    BigDecimal quantity = parts[3].isEmpty() ? null : new BigDecimal(parts[3]);
                    BigDecimal price = parts[4].isEmpty() ? null : new BigDecimal(parts[4]);
                    BigDecimal amount = new BigDecimal(parts[5]);
                    LocalDateTime timestamp = LocalDateTime.parse(parts[6], formatter);
                    String notes = parts.length > 7 ? parts[7].replace(";", ",") : "";
                    
                    Transaction tx = new Transaction(id, type, assetId, quantity, price, amount, timestamp, notes);
                    portfolio.addTransaction(tx);
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading transactions: " + e.getMessage());
        }
    }

    private void savePortfolioMetadata(Portfolio portfolio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(PORTFOLIO_FILE))) {
            writer.println(portfolio.getCashBalance().toString());
        } catch (IOException e) {
            System.err.println("Error saving portfolio metadata: " + e.getMessage());
        }
    }

    private void saveAssets(Portfolio portfolio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(ASSETS_FILE))) {
            for (Holding holding : portfolio.getHoldings().values()) {
                writer.println(holding.getAsset().toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving assets: " + e.getMessage());
        }
    }

    private void saveHoldings(Portfolio portfolio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HOLDINGS_FILE))) {
            for (Holding holding : portfolio.getHoldings().values()) {
                writer.println(holding.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving holdings: " + e.getMessage());
        }
    }

    private void saveTransactions(Portfolio portfolio) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction tx : portfolio.getTransactions()) {
                writer.println(tx.toCsv());
            }
        } catch (IOException e) {
            System.err.println("Error saving transactions: " + e.getMessage());
        }
    }
}
