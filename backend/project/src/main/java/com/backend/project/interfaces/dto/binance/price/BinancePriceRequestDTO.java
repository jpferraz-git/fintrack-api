package com.backend.project.interfaces.dto.binance.price;

import java.math.BigDecimal;

public record BinancePriceRequestDTO(
        String symbol,
        BigDecimal price
){
}
