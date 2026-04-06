package com.backend.project.interfaces.dto.asset;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record AssetResponseDTO (
        UUID assetId,
        UUID fkUser,
        String symbol,
        String type,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal avgPrice,
        Instant createdAt,
        Instant updatedAt
){ }
