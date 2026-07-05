package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.exception.AssetAlreadyExistsException;
import com.backend.project.exception.AssetNotFoundException;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.springdata.AssetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Component
@AllArgsConstructor
public class AssetGateway implements AssetRepository {

    private final AssetJpaRepository jpaRepository;

    @Override
    public AssetEntity create(AssetEntity asset) {
        if (asset.getUserId() == null || asset.getUserId().getId() == null) {
            throw new IllegalArgumentException("Asset user is required.");
        }

        if (jpaRepository.existsBySymbolAndUserId(asset.getSymbol(), asset.getUserId().getId())) {
            throw new AssetAlreadyExistsException(asset.getSymbol());
        }
        return jpaRepository.save(asset);
    }


    @Override
    public AssetEntity findBySymbolAndUserId(String symbol, UUID userId) {
        List<AssetEntity> assets = jpaRepository.findAllBySymbolAndUserId(symbol, userId);
        if (assets.isEmpty()) {
            throw new AssetNotFoundException(symbol);
        }

        if (assets.size() == 1) {
            return assets.get(0);
        }

        // Self-heal duplicated rows for same user+symbol by merging into a single position.
        AssetEntity primary = assets.get(0);

        BigDecimal mergedQuantity = BigDecimal.ZERO;
        BigDecimal mergedCost = BigDecimal.ZERO;
        for (AssetEntity asset : assets) {
            BigDecimal quantity = asset.getQuantity() != null ? asset.getQuantity() : BigDecimal.ZERO;
            BigDecimal avgPrice = asset.getAvgPrice() != null ? asset.getAvgPrice() : BigDecimal.ZERO;

            mergedQuantity = mergedQuantity.add(quantity);
            mergedCost = mergedCost.add(quantity.multiply(avgPrice));
        }

        if (mergedQuantity.compareTo(BigDecimal.ZERO) <= 0) {
            jpaRepository.deleteAll(assets);
            throw new AssetNotFoundException(symbol);
        }

        BigDecimal mergedAvgPrice = mergedCost.divide(mergedQuantity, 8, RoundingMode.HALF_UP);

        primary.setQuantity(mergedQuantity);
        primary.setAvgPrice(mergedAvgPrice);
        if (primary.getType() == null || primary.getType().isBlank()) {
            primary.setType("CRYPTO");
        }

        AssetEntity normalizedAsset = jpaRepository.save(primary);
        for (int i = 1; i < assets.size(); i++) {
            jpaRepository.delete(assets.get(i));
        }

        return normalizedAsset;
    }

    @Override
    public AssetEntity update(AssetEntity asset) {
        AssetEntity current = jpaRepository.findById(asset.getId())
            .orElseThrow(() -> new AssetNotFoundException(asset.getId()));

        if (asset.getUserId() == null) {
            asset.setUserId(current.getUserId());
        }

        return jpaRepository.save(asset);
    }

    @Override
    public void deleteBySymbolAndUserId(String symbol, UUID userId) {
        List<AssetEntity> assets = jpaRepository.findAllBySymbolAndUserId(symbol, userId);
        if (assets.isEmpty()) {
            throw new AssetNotFoundException(symbol);
        }
        jpaRepository.deleteAll(assets);
    }

    @Override
    public AssetEntity getReferenceById(UUID uuid) {
        return jpaRepository.findById(uuid)
                .orElseThrow(() -> new AssetNotFoundException(uuid));
    }

    @Override
    public Page<AssetEntity> findAllByUserId(UUID userId, Pageable pageable) {
        return jpaRepository.findAllByUserId(userId, pageable);
    }

    @Override
    public BigDecimal getQuantityByUser(UUID userId) {
        BigDecimal quantity = jpaRepository.getQuantityByUserId(userId);
        return quantity != null ? quantity : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getAssetQuantityByUserAndSymbol(UUID userId, String symbol) {
        BigDecimal quantity = jpaRepository.getAssetQuantityByUserIdAndSymbol(userId, symbol);
        return quantity != null ? quantity : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getActualValue(UUID userId) {
        throw new UnsupportedOperationException("Method getActualValue not implemented yet.");
    }

    @Override
    public BigDecimal getUserProfitPercentage(UUID userId, String symbol, BigDecimal marketPrice) {
        BigDecimal percentage = jpaRepository.getUserProfitPercentage(userId, symbol, marketPrice);
        return percentage != null ? percentage : BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getUserProfitValue(UUID userId, String symbol, BigDecimal marketPrice) {
        BigDecimal profitValue = jpaRepository.getUserProfitValue(userId, symbol, marketPrice);
        return profitValue != null ? profitValue : BigDecimal.ZERO;
    }
}
