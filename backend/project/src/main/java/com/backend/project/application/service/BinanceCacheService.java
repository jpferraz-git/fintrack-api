package com.backend.project.application.service;

import com.backend.project.infrastructure.binance.BinanceIntegration;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import com.backend.project.interfaces.dto.binance.ticker.Binance24hTickerRequestDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BinanceCacheService {

    private final BinanceIntegration binanceIntegration;

    public BinanceCacheService(BinanceIntegration binanceIntegration) {
        this.binanceIntegration = binanceIntegration;
    }

    @Cacheable(value = "binancePrice", key = "#symbol", unless = "#result == null")
    public BinancePriceResponseDTO getPrice(String symbol) {
        return binanceIntegration.getPrice(symbol);
    }

    @Cacheable(value = "binance24hTicker", key = "#symbol", unless = "#result == null")
    public Binance24hTickerRequestDTO get24hTicker(String symbol) {
        return binanceIntegration.get24hTicker(symbol);
    }
}
