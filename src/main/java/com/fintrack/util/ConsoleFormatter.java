package com.fintrack.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class ConsoleFormatter {
    private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getNumberInstance(new Locale("en", "IN"));
    private static final NumberFormat PERCENT_FORMAT = NumberFormat.getPercentInstance();
    
    static {
        PERCENT_FORMAT.setMinimumFractionDigits(2);
        PERCENT_FORMAT.setMaximumFractionDigits(2);
        CURRENCY_FORMAT.setMinimumFractionDigits(2);
        CURRENCY_FORMAT.setMaximumFractionDigits(2);
    }

    public static String formatCurrency(BigDecimal amount) {
        if (amount == null) return "INR " + CURRENCY_FORMAT.format(0);
        return "INR " + CURRENCY_FORMAT.format(amount);
    }

    public static String formatPercent(BigDecimal fraction) {
        if (fraction == null) return PERCENT_FORMAT.format(0);
        return PERCENT_FORMAT.format(fraction);
    }
    
    public static void printHeader(String title) {
        System.out.println();
        System.out.println("=".repeat(40));
        System.out.println(title.toUpperCase());
        System.out.println("=".repeat(40));
    }
    
    public static void printSeparator() {
        System.out.println("-".repeat(40));
    }
}
