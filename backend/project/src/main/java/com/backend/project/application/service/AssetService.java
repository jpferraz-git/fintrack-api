package com.backend.project.application.service;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.stereotype.Service;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetEntity create(AssetEntity asset){
        return assetRepository.create(asset);
    }
}
