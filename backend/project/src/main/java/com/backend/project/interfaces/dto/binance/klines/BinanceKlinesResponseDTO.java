package com.backend.project.interfaces.dto.binance.klines;

import java.math.BigDecimal;
import java.time.Instant;

public record BinanceKlinesResponseDTO(
        Long id,
        Instant openTime,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume,
        Instant closeTime,
        BigDecimal quoteAssetVolume,
        Integer numberOfTrades,
        BigDecimal takerBuyBaseAssetVolume,
        BigDecimal takerBuyQuoteAssetVolume,
        Instant created_at,
        Instant updated_at
) {
}

