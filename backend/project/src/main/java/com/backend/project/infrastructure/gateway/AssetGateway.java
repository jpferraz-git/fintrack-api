package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.exception.AssetAlreadyExistsException;
import com.backend.project.exception.AssetNotFoundException;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.springdata.AssetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
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
        AssetEntity asset = jpaRepository.findBySymbolAndUserId(symbol, userId);
        if (asset == null) {
            throw new AssetNotFoundException(symbol);
        }
        return asset;
    }

    @Override
    public AssetEntity update(AssetEntity asset) {
        AssetEntity current = jpaRepository.findById(asset.getId())
            .orElseThrow(() -> new AssetNotFoundException(asset.getId()));

        if (asset.getUserId() == null) {
            asset.setUserId(current.getUserId());
        }

        UUID userId = asset.getUserId().getId();

        AssetEntity bySymbol = jpaRepository.findBySymbolAndUserId(asset.getSymbol(), userId);
        if (bySymbol != null && !bySymbol.getId().equals(current.getId())) {
            throw new AssetAlreadyExistsException(asset.getSymbol());
        }

        return jpaRepository.save(asset);
    }

    @Override
    public void deleteBySymbolAndUserId(String symbol, UUID userId) {
        jpaRepository.delete(findBySymbolAndUserId(symbol, userId));
    }

    @Override
    public AssetEntity getReferenceById(UUID uuid) {
        return jpaRepository.findById(uuid)
                .orElseThrow(() -> new AssetNotFoundException(uuid));
    }

    @Override
    public List<AssetEntity> findAllByUserId(UUID userId) {
        return jpaRepository.findAllByUserId(userId);
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
