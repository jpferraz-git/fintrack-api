package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.BinanceRepository;
import com.backend.project.infrastructure.entity.BinancePriceEntity;
import com.backend.project.infrastructure.springdata.BinanceJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class BinanceGateway implements BinanceRepository {

    private final BinanceJpaRepository binanceJpaRepository;

    public BinanceGateway(BinanceJpaRepository binanceJpaRepository) {
        this.binanceJpaRepository = binanceJpaRepository;
    }

    @Override
    public BinancePriceEntity createBinancePrice(BinancePriceEntity entity) {
        return binanceJpaRepository.save(entity);
    }
}
