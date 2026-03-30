package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceInvalidSymbolException extends BinanceApiException {
    public BinanceInvalidSymbolException() {
        super("Invalid symbol", -1121);
    }
}

