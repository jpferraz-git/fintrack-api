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

        if (jpaRepository.existsByTickerAndUserId(asset.getTicker(), asset.getUserId().getId())) {
            throw new AssetAlreadyExistsException(asset.getTicker());
        }
        return jpaRepository.save(asset);
    }


    @Override
    public AssetEntity findByTickerAndUserId(String ticker, UUID userId) {
        AssetEntity asset = jpaRepository.findByTickerAndUserId(ticker, userId);
        if (asset == null) {
            throw new AssetNotFoundException(ticker);
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

        AssetEntity byTicker = jpaRepository.findByTickerAndUserId(asset.getTicker(), userId);
        if (byTicker != null && !byTicker.getId().equals(current.getId())) {
            throw new AssetAlreadyExistsException(asset.getTicker());
        }

        return jpaRepository.save(asset);
    }

    @Override
    public void deleteByTickerAndUserId(String ticker, UUID userId) {
        jpaRepository.delete(findByTickerAndUserId(ticker, userId));
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
    public Long getQuantityByUser(UUID userId) {
        throw new UnsupportedOperationException("Method getQuantityByUser not implemented yet.");
    }

    @Override
    public BigDecimal getActualValue(UUID userId) {
        throw new UnsupportedOperationException("Method getActualValue not implemented yet.");
    }
}
