package com.backend.project.exception;

public class BatchAlreadyExistsException extends RuntimeException {

    public BatchAlreadyExistsException(String identifier) {
        super("Batch with identifier '" + identifier + "' already exists.");
    }
}

