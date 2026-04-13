package com.backend.project.interfaces.dto.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponseDTO (
    UUID id,
    UUID fkUser,
    String symbol,
    String type,
    BigDecimal quantity,
    BigDecimal price,
    Instant createdAt,
    Instant updatedAt
) {
}
