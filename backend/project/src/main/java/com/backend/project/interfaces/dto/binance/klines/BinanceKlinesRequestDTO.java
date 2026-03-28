package com.backend.project.interfaces.dto.binance.klines;

import java.math.BigDecimal;

public record BinanceKlinesRequestDTO(
        Long openTime,
        BigDecimal open,
        BigDecimal high,
        BigDecimal low,
        BigDecimal close,
        BigDecimal volume,
        Long closeTime,
        BigDecimal quoteAssetVolume,
        Integer numberOfTrades,
        BigDecimal takerBuyBaseAssetVolume,
        BigDecimal takerBuyQuoteAssetVolume
) {
}

