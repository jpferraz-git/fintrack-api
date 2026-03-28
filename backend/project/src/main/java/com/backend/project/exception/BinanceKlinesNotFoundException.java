package com.backend.project.exception;

public class BinanceKlinesNotFoundException extends RuntimeException {

    public BinanceKlinesNotFoundException(String symbol, String interval) {
        super("No klines data found for symbol '" + symbol + "' and interval '" + interval + "'.");
    }
}

