package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.exception.AssetAlreadyExistsException;
import com.backend.project.exception.AssetNotFoundException;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.springdata.AssetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AssetGateway implements AssetRepository {

    private final AssetJpaRepository jpaRepository;

    @Override
    public AssetEntity create(AssetEntity asset) {
        if (jpaRepository.existsByTicker(asset.getTicker())) {
            throw new AssetAlreadyExistsException(asset.getTicker());
        }
        return jpaRepository.save(asset);
    }


    @Override
    public AssetEntity findByTicker(String ticker) {
        AssetEntity asset = jpaRepository.findByTicker(ticker);
        if (asset == null) {
            throw new AssetNotFoundException(ticker);
        }
        return asset;
    }

    @Override
    public AssetEntity update(AssetEntity asset) {
        AssetEntity current = jpaRepository.findById(asset.getAssetId())
                .orElseThrow(() -> new AssetNotFoundException(asset.getAssetId()));

        AssetEntity byTicker = jpaRepository.findByTicker(asset.getTicker());
        if (byTicker != null && !byTicker.getAssetId().equals(current.getAssetId())) {
            throw new AssetAlreadyExistsException(asset.getTicker());
        }

        return jpaRepository.save(asset);
    }

    @Override
    public void deleteByTicker(String ticker) {
        jpaRepository.delete(findByTicker(ticker));
    }

    @Override
    public AssetEntity getReferenceById(UUID uuid) {
        return jpaRepository.findById(uuid)
                .orElseThrow(() -> new AssetNotFoundException(uuid));
    }

    @Override
    public List<AssetEntity> findAll() {
        return jpaRepository.findAll();
    }
}
