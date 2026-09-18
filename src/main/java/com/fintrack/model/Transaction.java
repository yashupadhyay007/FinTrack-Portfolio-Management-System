package com.fintrack.model;

import com.fintrack.enums.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    private String id;
    private TransactionType type;
    private String assetId; // can be null for deposit/withdraw
    private BigDecimal quantity; // can be null for deposit/withdraw
    private BigDecimal price; // can be null for deposit/withdraw
    private BigDecimal amount;
    private LocalDateTime timestamp;
    private String notes;

    public Transaction(String id, TransactionType type, String assetId, BigDecimal quantity, BigDecimal price, BigDecimal amount, LocalDateTime timestamp, String notes) {
        this.id = id;
        this.type = type;
        this.assetId = assetId;
        this.quantity = quantity;
        this.price = price;
        this.amount = amount;
        this.timestamp = timestamp;
        this.notes = notes;
    }

    public String getId() { return id; }
    public TransactionType getType() { return type; }
    public String getAssetId() { return assetId; }
    public BigDecimal getQuantity() { return quantity; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public String getNotes() { return notes; }

    public String toCsv() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return String.join(",", 
            id, 
            type.name(), 
            assetId != null ? assetId : "", 
            quantity != null ? quantity.toString() : "", 
            price != null ? price.toString() : "", 
            amount.toString(), 
            timestamp.format(formatter), 
            notes != null ? notes.replace(",", ";") : "" // Escape commas in notes
        );
    }
}
