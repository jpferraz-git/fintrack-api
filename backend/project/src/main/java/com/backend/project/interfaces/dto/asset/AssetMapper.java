package com.backend.project.interfaces.dto.asset;

import com.backend.project.domain.model.AssetModel;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

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

    public AssetResponseDTO toResponse(AssetModel dto){
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
