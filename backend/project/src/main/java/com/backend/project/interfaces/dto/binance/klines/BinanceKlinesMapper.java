package com.backend.project.interfaces.dto.binance.klines;

import com.backend.project.domain.model.BinanceKlinesModel;
import com.backend.project.infrastructure.entity.BinanceKlinesEntity;

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

    public BinanceKlinesModel toBinanceKlinesModel(BinanceKlinesRequestDTO dto) {
        return new BinanceKlinesModel(
                dto.openTime(),
                dto.open(),
                dto.high(),
                dto.low(),
                dto.close(),
                dto.volume(),
                dto.closeTime(),
                dto.quoteAssetVolume(),
                dto.numberOfTrades(),
                dto.takerBuyBaseAssetVolume(),
                dto.takerBuyQuoteAssetVolume()
        );
    }

    public BinanceKlinesResponseDTO toBinanceKlinesResponseDTO(BinanceKlinesEntity entity) {
        return new BinanceKlinesResponseDTO(
                entity.getId(),
                entity.getOpenTime(),
                entity.getOpen(),
                entity.getHigh(),
                entity.getLow(),
                entity.getClose(),
                entity.getVolume(),
                entity.getCloseTime(),
                entity.getQuoteAssetVolume(),
                entity.getNumberOfTrades(),
                entity.getTakerBuyBaseAssetVolume(),
                entity.getTakerBuyQuoteAssetVolume(),
                entity.getCreated_at(),
                entity.getUpdated_at()
        );
    }
}

