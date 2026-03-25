package com.backend.project.interfaces.dto.transaction;

public record TransactionRequestDTO (
        String operationType,
        int quantity,
        double unitPrice
){}
