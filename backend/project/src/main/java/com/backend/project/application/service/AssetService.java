package com.backend.project.application.service;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.asset.AssetMapper;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final AssetMapper assetMapper;

    public AssetService(AssetRepository assetRepository, AssetMapper assetMapper) {
        this.assetRepository = assetRepository;
        this.assetMapper = assetMapper;
    }

    public AssetResponseDTO create(AssetRequestDTO asset){
        AssetEntity saved = assetRepository.create(
                assetMapper.toEntity(assetMapper.toModel(asset))
        );
        return assetMapper.toResponse(saved);
    }

    public AssetResponseDTO update(String ticker, AssetRequestDTO asset){
        AssetEntity assetEntity = assetRepository.findByTicker(ticker);
        assetEntity.setTicker(asset.ticker());
        assetEntity.setAssetType(asset.assetType());
        assetEntity.setCompanyName(asset.companyName());
        AssetEntity updated = assetRepository.update(assetEntity);
        return assetMapper.toResponse(updated);
    }

    public List<AssetResponseDTO> findAll(){
        return assetRepository.findAll().stream()
                .map(assetMapper::toResponse)
                .toList();
    }
}
