package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceNoSuchOrderException extends BinanceApiException {
    public BinanceNoSuchOrderException() {
        super("Order not found", -2013);
    }
}

