package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceTimeoutException extends BinanceApiException {
    public BinanceTimeoutException() {
        super("Timeout from Binance API", -1007);
    }
}
