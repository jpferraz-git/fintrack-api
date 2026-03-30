package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceInvalidTimestampException extends BinanceApiException {
    public BinanceInvalidTimestampException() {
        super("Timestamp outside recvWindow", -1021);
    }
}

