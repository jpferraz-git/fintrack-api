package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceUnauthorizedException extends BinanceApiException {
    public BinanceUnauthorizedException() {
        super("Invalid API key or insufficient permissions", -1002);
    }
}

