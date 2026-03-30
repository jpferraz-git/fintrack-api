package com.backend.project.exception;

public class BinanceApiException extends RuntimeException{

    private final int binanceCode;

    public BinanceApiException(String message, int binanceCode) {
        super(message);
        this.binanceCode = binanceCode;
    }

    public int getBinanceCode() { return binanceCode; }
}
