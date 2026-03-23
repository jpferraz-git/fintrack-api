package com.backend.project.exception;

public class TransactionAlreadyExistsException extends RuntimeException {

    public TransactionAlreadyExistsException(String identifier) {
        super("Transaction with identifier '" + identifier + "' already exists.");
    }
}

