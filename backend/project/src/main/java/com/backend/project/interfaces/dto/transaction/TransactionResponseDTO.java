package com.backend.project.interfaces.dto.transaction;

import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.entity.UserEntity;

import java.time.Instant;
import java.util.Date;
import java.util.UUID;

public record TransactionResponseDTO (
    UUID id,
    UUID userId,
    UUID assetId,
    UUID batchId,
    String operationType,
    int quantity,
    double unitPrice,
    Date operationDate,
    Instant createdAt,
    Instant updatedAt
) {
}
