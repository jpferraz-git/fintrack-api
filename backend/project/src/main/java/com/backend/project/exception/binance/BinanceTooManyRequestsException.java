package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceTooManyRequestsException extends BinanceApiException {
    public BinanceTooManyRequestsException() {
        super("Too many requests", -1003);
    }
}

