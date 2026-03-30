package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceIllegalCharsException extends BinanceApiException {
    public BinanceIllegalCharsException() {
        super("Illegal characters in request parameters", -1100);
    }
}

