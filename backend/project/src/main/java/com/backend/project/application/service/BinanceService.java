package com.backend.project.application.service;

import com.backend.project.infrastructure.external.binance.BinanceIntegration;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class BinanceService {

    private final BinanceIntegration binanceIntegration;

    public BinanceService(BinanceIntegration binanceIntegration) {
        this.binanceIntegration = binanceIntegration;
    }

    public BinancePriceResponseDTO getPrice(String symbol) {
        return binanceIntegration.getPrice(symbol);
    }

    public String get24hPrice(String symbol){
        return binanceIntegration.get24hTicker(symbol);
    }
}
