package com.backend.project.interfaces.dto.binance;

public class BinanceMapper {

    public BinancePriceResponseDTO toBinancePriceResponseDTO(String symbol, String price) {
        return new BinancePriceResponseDTO(symbol, price);
    }
}
