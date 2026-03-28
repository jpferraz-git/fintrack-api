package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.BinancePriceEntity;

public interface BinanceRepository {

    BinancePriceEntity createBinancePrice(BinancePriceEntity entity);
}
