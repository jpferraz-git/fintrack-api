package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.AssetEntity;

import java.util.List;
import java.util.UUID;

public interface AssetRepository {

    AssetEntity create(AssetEntity asset);
    AssetEntity getReferenceById(UUID uuid);
    AssetEntity findByTicker(String ticker);
    AssetEntity update(AssetEntity asset);
    List<AssetEntity> findAll();
}
