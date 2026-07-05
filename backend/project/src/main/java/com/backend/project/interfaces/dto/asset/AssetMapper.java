package com.backend.project.interfaces.dto.asset;


import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.stereotype.Component;

@Component
public class AssetMapper {

    public AssetEntity toEntity(AssetRequestDTO dto) {
        return new AssetEntity(
                null,
                null,
                dto.symbol(),
                dto.type(),
                dto.quantity(),
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
                dto.getAvgPrice(),
                dto.getCreatedAt(),
                dto.getUpdatedAt()
        );
    }
}
