package com.backend.project.interfaces.dto.binance.klines;


import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class BinanceKlinesMapper {



    public BinanceKlinesResponseDTO toBinanceKlinesResponseDTO(BinanceKlinesRequestDTO dto) {
        return toResponseKlines(dto);
    }

    public BinanceKlinesResponseDTO toResponseKlines(BinanceKlinesRequestDTO dto) {
        return new BinanceKlinesResponseDTO(
                Instant.ofEpochMilli(dto.openTime()),
                dto.open(),
                dto.high(),
                dto.low(),
                dto.close(),
                dto.volume(),
                Instant.ofEpochMilli(dto.closeTime()),
                dto.quoteAssetVolume(),
                dto.numberOfTrades(),
                dto.takerBuyBaseAssetVolume(),
                dto.takerBuyQuoteAssetVolume()
        );
    }

    public List<BinanceKlinesResponseDTO> toResponseKlines(List<BinanceKlinesRequestDTO> dtos) {
        return dtos.stream().map(this::toResponseKlines).toList();
    }

}

