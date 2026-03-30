package com.backend.project.exception;

import java.util.UUID;

public class BatchNotFoundException extends RuntimeException {

    public BatchNotFoundException(UUID batchId) {
        super("Batch with id '" + batchId + "' does not exist.");
    }

    public BatchNotFoundException(String identifier) {
        super("Batch with identifier '" + identifier + "' does not exist.");
    }
}


