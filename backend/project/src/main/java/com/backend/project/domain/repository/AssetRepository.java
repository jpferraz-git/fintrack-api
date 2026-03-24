package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.AssetEntity;

import java.util.List;
import java.util.UUID;

public interface AssetRepository {

    AssetEntity create(AssetEntity asset);
    AssetEntity getReferenceById(UUID uuid);
    List<AssetEntity> findAll();
}
