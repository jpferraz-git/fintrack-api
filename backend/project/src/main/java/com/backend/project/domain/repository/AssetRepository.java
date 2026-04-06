package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.AssetEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AssetRepository {

    AssetEntity create(AssetEntity asset);
    AssetEntity getReferenceById(UUID uuid);
    AssetEntity findByTickerAndUserId(String ticker, UUID userId);

    AssetEntity update(AssetEntity asset);
    void deleteByTickerAndUserId(String ticker, UUID userId);
    List<AssetEntity> findAllByUserId(UUID userId);

    Long getQuantityByUser(UUID userId);
    BigDecimal getActualValue(UUID userId);
}
