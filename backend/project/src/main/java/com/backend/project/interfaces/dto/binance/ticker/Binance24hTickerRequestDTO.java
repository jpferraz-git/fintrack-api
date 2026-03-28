package com.backend.project.interfaces.dto.binance.ticker;

import java.math.BigDecimal;

public record Binance24hTickerRequestDTO(
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
        Long openTime,
        Long closeTime,
        Long firstId,
        Long lastId,
        Long count
) {
}

