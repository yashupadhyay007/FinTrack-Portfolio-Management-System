package com.fintrack.exception;

public class InsufficientUnitsException extends RuntimeException {
    public InsufficientUnitsException(String message) {
        super(message);
    }
}
