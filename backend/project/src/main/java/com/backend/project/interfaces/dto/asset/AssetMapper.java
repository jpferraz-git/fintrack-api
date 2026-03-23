package com.backend.project.interfaces.dto.asset;

import com.backend.project.domain.model.AssetModel;
import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public AssetEntity toEntity(AssetRequestDTO dto){
        return new AssetEntity(
                null,
                dto.ticker(),
                dto.assetType(),
                dto.companyName(),
                null,
                null
        );
    }
    public AssetModel toModel(AssetRequestDTO dto) {
        return new AssetModel(
                null,
                dto.ticker(),
                dto.assetType(),
                dto.companyName(),
                null,
                null
        );
    }

    public AssetResponseDTO toResponse(AssetEntity dto){
        return new AssetResponseDTO(
                dto.getAssetId(),
                dto.getAssetType(),
                dto.getAssetType(),
                dto.getCompanyName(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
