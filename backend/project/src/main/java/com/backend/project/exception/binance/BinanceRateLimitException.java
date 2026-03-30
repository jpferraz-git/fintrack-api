package com.backend.project.exception.binance;


import com.backend.project.exception.BinanceApiException;

public class BinanceRateLimitException extends BinanceApiException {
    public BinanceRateLimitException() {
        super("Too many requests", -1003);
    }
}