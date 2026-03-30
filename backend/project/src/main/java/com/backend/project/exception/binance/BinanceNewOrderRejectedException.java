package com.backend.project.exception.binance;

import com.backend.project.exception.BinanceApiException;

public class BinanceNewOrderRejectedException extends BinanceApiException {
    public BinanceNewOrderRejectedException() {
        super("New order rejected", -2010);
    }
}

