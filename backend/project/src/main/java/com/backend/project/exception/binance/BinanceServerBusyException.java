package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceServerBusyException extends BinanceApiException {
    public BinanceServerBusyException() {
        super("Binance server busy", -1008);
    }
}
