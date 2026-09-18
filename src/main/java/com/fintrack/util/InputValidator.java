package com.fintrack.util;

import java.math.BigDecimal;
import java.util.Scanner;

public class InputValidator {

    public static String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public static String readStringRequired(Scanner scanner, String prompt) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please try again.");
            }
        } while (input.isEmpty());
        return input;
    }

    public static int readInt(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int value = Integer.parseInt(scanner.nextLine().trim());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
    }

    public static BigDecimal readBigDecimal(Scanner scanner, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine().trim();
                BigDecimal value = new BigDecimal(input);
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    public static BigDecimal readPositiveBigDecimal(Scanner scanner, String prompt) {
        while (true) {
            BigDecimal value = readBigDecimal(scanner, prompt);
            if (value.compareTo(BigDecimal.ZERO) > 0) {
                return value;
            }
            System.out.println("Value must be greater than zero.");
        }
    }
    
    public static BigDecimal readNonNegativeBigDecimal(Scanner scanner, String prompt) {
        while (true) {
            BigDecimal value = readBigDecimal(scanner, prompt);
            if (value.compareTo(BigDecimal.ZERO) >= 0) {
                return value;
            }
            System.out.println("Value cannot be negative.");
        }
    }
}
