package com.backend.project.interfaces.dto.asset;

import java.math.BigDecimal;
import java.util.UUID;

public record AssetRequestDTO (
        UUID fkUser,
        String symbol,
        String type,
        BigDecimal quantity,
        BigDecimal price,
        BigDecimal avgPrice
){}
