package com.backend.project.interfaces.dto.binance.price;

import com.backend.project.domain.model.BinancePriceModel;
import com.backend.project.infrastructure.entity.BinancePriceEntity;

public class BinancePriceMapper {

    public BinancePriceEntity toBinancePriceEntity(BinancePriceModel model){
        return new BinancePriceEntity(
                model.getSymbol(),
                model.getPrice()
        );
    }

    public BinancePriceModel toBinancePriceModel(BinancePriceRequestDTO dto){
        return new BinancePriceModel(
                dto.symbol(),
                dto.price()
        );
    }

    public BinancePriceResponseDTO toBinancePriceResponseDTO(BinancePriceEntity entity) {
        return new BinancePriceResponseDTO(
                entity.getId(),
                entity.getSymbol(),
                entity.getPrice(),
                entity.getCreated_at(),
                entity.getUpdated_at()
        );
    }
}
