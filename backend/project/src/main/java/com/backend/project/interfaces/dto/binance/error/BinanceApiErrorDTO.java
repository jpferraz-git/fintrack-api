package com.backend.project.interfaces.dto.binance.error;

public record BinanceApiErrorDTO(
        Integer code,
        String msg
) {
}

