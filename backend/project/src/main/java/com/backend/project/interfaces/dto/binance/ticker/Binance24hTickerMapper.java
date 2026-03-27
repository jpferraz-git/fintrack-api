package com.backend.project.interfaces.dto.binance.ticker;

import com.backend.project.domain.model.Binance24hTickerModel;
import com.backend.project.infrastructure.entity.Binance24hTickerEntity;

public class Binance24hTickerMapper {

    public Binance24hTickerEntity toBinance24hTickerEntity(Binance24hTickerModel model) {
        return new Binance24hTickerEntity(
                model.getSymbol(),
                model.getPriceChange(),
                model.getPriceChangePercent(),
                model.getWeightedAvgPrice(),
                model.getPrevClosePrice(),
                model.getLastPrice(),
                model.getLastQty(),
                model.getBidPrice(),
                model.getLowPrice(),
                model.getVolume(),
                model.getQuoteVolume(),
                model.getOpenTime(),
                model.getCloseTime(),
                model.getFirstId(),
                model.getLastId(),
                model.getCount()
        );
    }

    public Binance24hTickerModel toBinance24hTickerModel(Binance24hTickerRequestDTO dto) {
        return new Binance24hTickerModel(
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
                dto.openTime(),
                dto.closeTime(),
                dto.firstId(),
                dto.lastId(),
                dto.count()
        );
    }

    public Binance24hTickerResponseDTO toBinance24hTickerResponseDTO(Binance24hTickerEntity entity) {
        return new Binance24hTickerResponseDTO(
                entity.getId(),
                entity.getSymbol(),
                entity.getPriceChange(),
                entity.getPriceChangePercent(),
                entity.getWeightedAvgPrice(),
                entity.getPrevClosePrice(),
                entity.getLastPrice(),
                entity.getLastQty(),
                entity.getBidPrice(),
                entity.getLowPrice(),
                entity.getVolume(),
                entity.getQuoteVolume(),
                entity.getOpenTime(),
                entity.getCloseTime(),
                entity.getFirstId(),
                entity.getLastId(),
                entity.getCount(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}

