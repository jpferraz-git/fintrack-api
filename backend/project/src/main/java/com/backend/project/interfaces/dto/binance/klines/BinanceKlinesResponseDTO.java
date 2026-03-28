package com.backend.project.interfaces.dto.binance.klines;

import java.math.BigDecimal;
import java.time.Instant;

public record BinanceKlinesResponseDTO(
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
        BigDecimal takerBuyQuoteAssetVolume
) {
}

