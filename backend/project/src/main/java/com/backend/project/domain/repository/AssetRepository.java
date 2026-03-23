package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.AssetEntity;

public interface AssetRepository {

    AssetEntity create(AssetEntity asset);

}
