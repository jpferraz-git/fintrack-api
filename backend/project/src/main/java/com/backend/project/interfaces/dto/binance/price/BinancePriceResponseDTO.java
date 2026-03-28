package com.backend.project.interfaces.dto.binance.price;

import java.math.BigDecimal;
import java.time.Instant;

public record BinancePriceResponseDTO (
        Long id,
        String symbol,
        BigDecimal price,
        Instant created_at,
        Instant updated_at
){}