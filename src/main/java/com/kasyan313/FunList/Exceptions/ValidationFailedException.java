package com.kasyan313.FunList.Exceptions;

public class ValidationFailedException extends RuntimeException {
    public ValidationFailedException() {
        super("validation failed");
    }

    public ValidationFailedException(String message) {
        super(message);
    }
}
