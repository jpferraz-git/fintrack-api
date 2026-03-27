package com.backend.project.interfaces.dto.binance.ticker;

import java.math.BigDecimal;
import java.time.Instant;

public record Binance24hTickerResponseDTO(
        Long id,
        String symbol,
        BigDecimal priceChange,
        BigDecimal priceChangePercent,
        BigDecimal weightedAvgPrice,
        BigDecimal prevClosePrice,
        BigDecimal lastPrice,
        BigDecimal lastQty,
        BigDecimal bidPrice,
        BigDecimal lowPrice,
        BigDecimal volume,
        BigDecimal quoteVolume,
        Instant openTime,
        Instant closeTime,
        Long firstId,
        Long lastId,
        Long count,
        Instant created_at,
        Instant updated_at
) {
}

