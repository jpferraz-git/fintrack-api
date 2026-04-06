package com.backend.project.interfaces.dto.asset;

import java.time.Instant;
import java.util.UUID;

public record AssetResponseDTO (
        UUID assetId,
        UUID userId,
        String ticker,
        String assetType,
        String companyName,
        Instant createdAt,
        Instant updatedAt
){ }
