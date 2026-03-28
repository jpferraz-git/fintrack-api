package com.backend.project.interfaces.dto.binance.klines;

import com.backend.project.domain.model.BinanceKlinesModel;
import com.backend.project.infrastructure.entity.BinanceKlinesEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class BinanceKlinesMapper {

    public BinanceKlinesEntity toBinanceKlinesEntity(BinanceKlinesModel model) {
        return new BinanceKlinesEntity(
                model.getOpenTime(),
                model.getOpen(),
                model.getHigh(),
                model.getLow(),
                model.getClose(),
                model.getVolume(),
                model.getCloseTime(),
                model.getQuoteAssetVolume(),
                model.getNumberOfTrades(),
                model.getTakerBuyBaseAssetVolume(),
                model.getTakerBuyQuoteAssetVolume()
        );
    }

//
//    public BinanceKlinesModel toBinanceKlinesModel(BinanceKlinesRequestDTO dto) {
//        return new BinanceKlinesModel(
//                dto.openTime(),
//                dto.open(),
//                dto.high(),
//                dto.low(),
//                dto.close(),
//                dto.volume(),
//                dto.closeTime(),
//                dto.quoteAssetVolume(),
//                dto.numberOfTrades(),
//                dto.takerBuyBaseAssetVolume(),
//                dto.takerBuyQuoteAssetVolume()
//        );
//    }

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

