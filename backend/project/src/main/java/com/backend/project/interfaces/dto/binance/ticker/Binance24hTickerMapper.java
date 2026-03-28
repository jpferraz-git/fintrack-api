package com.backend.project.interfaces.dto.binance.ticker;

import com.backend.project.domain.model.Binance24hTickerModel;
import com.backend.project.infrastructure.entity.Binance24hTickerEntity;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
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
                Instant.ofEpochSecond(dto.openTime()),
                Instant.ofEpochSecond(dto.closeTime()),
                dto.firstId(),
                dto.lastId(),
                dto.count()
        );
    }

}

