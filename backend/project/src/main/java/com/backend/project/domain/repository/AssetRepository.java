package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.AssetEntity;

import java.math.BigDecimal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssetRepository {

    AssetEntity create(AssetEntity asset);
    AssetEntity getReferenceById(UUID uuid);
    AssetEntity findBySymbolAndUserId(String symbol, UUID userId);

    AssetEntity update(AssetEntity asset);
    void deleteBySymbolAndUserId(String symbol, UUID userId);
    Page<AssetEntity> findAllByUserId(UUID userId, Pageable pageable);

    BigDecimal getQuantityByUser(UUID userId);
    BigDecimal getAssetQuantityByUserAndSymbol(UUID userId, String symbol);
    BigDecimal getActualValue(UUID userId);
    BigDecimal getUserProfitPercentage(UUID userId, String symbol, BigDecimal marketPrice);
    BigDecimal getUserProfitValue(UUID userId, String symbol, BigDecimal marketPrice);
}
