package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceInvalidSignatureException extends BinanceApiException {
    public BinanceInvalidSignatureException() {
        super("Invalid signature", -1022);
    }
}

