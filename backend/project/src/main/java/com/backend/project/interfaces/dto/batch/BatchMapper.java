package com.backend.project.interfaces.dto.batch;

import com.backend.project.domain.model.BatchModel;
import com.backend.project.infrastructure.entity.BatchEntity;
import org.springframework.stereotype.Component;

@Component
public class BatchMapper {
    public BatchEntity toEntity(BatchModel model) {
        return new BatchEntity(
                null,
                model.getUploadDate(),
                model.getFileName(),
                model.getStatus(),
                null,
                null
        );
    }
    public BatchModel toModel(BatchRequestDTO dto) {
        return new BatchModel(
                null,
                null,
                dto.fileName(),
                dto.status(),
                null,
                null
        );
    }

    public BatchResponseDTO toResponse(BatchEntity entity) {
        return new BatchResponseDTO(
                entity.getId(),
                entity.getUploadDate(),
                entity.getFileName(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
