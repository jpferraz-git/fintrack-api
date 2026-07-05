package com.backend.project.interfaces.dto.binance.ticker;


import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class Binance24hTickerMapper {



    public Binance24hTickerResponseDTO toBinance24hTickerResponseDTO(Binance24hTickerRequestDTO dto) {
        return new Binance24hTickerResponseDTO(
                dto.symbol(),
                dto.priceChange(),
                dto.priceChangePercent(),
                dto.weightedAvgPrice(),
                dto.prevClosePrice(),
                dto.lastPrice(),
                dto.lastQty(),
                dto.bidPrice(),
                dto.lowPrice(),
                dto.volume(),
                dto.quoteVolume(),
                Instant.ofEpochSecond(dto.openTime()),
                Instant.ofEpochSecond(dto.closeTime()),
                dto.firstId(),
                dto.lastId(),
                dto.count()
        );
    }



}

