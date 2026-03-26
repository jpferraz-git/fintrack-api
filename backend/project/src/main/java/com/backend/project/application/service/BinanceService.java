package com.backend.project.application.service;

import com.backend.project.infrastructure.external.binance.BinanceIntegration;
import org.springframework.stereotype.Service;

@Service
public class BinanceService {

    private final BinanceIntegration binanceIntegration;

    public BinanceService(BinanceIntegration binanceIntegration) {
        this.binanceIntegration = binanceIntegration;
    }

    public String getPrice(String symbol) {
        return binanceIntegration.getPrice(symbol);
    }
}
