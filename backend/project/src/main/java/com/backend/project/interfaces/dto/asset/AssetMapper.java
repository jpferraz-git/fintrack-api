package com.backend.project.interfaces.dto.asset;

import com.backend.project.domain.model.AssetModel;
import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public AssetEntity toEntity(AssetModel model){
        return new AssetEntity(
                null,
                null,
                model.getSymbol(),
                model.getType(),
                model.getQuantity(),
                model.getPrice(),
                model.getAvgPrice(),
                null,
                null
        );
    }
    public AssetModel toModel(AssetRequestDTO dto) {
        return new AssetModel(
                null,
                dto.fkUser(),
            dto.symbol(),
            dto.type(),
            dto.quantity(),
            dto.price(),
            dto.avgPrice(),
                null,
                null
        );
    }

    public AssetResponseDTO toResponse(AssetEntity dto){
        return new AssetResponseDTO(
                dto.getId(),
                dto.getUserId() != null ? dto.getUserId().getId() : null,
                dto.getSymbol(),
                dto.getType(),
                dto.getQuantity(),
                dto.getPrice(),
                dto.getAvgPrice(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
