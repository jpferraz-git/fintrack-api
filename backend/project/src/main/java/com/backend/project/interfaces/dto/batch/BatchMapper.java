package com.backend.project.interfaces.dto.batch;


import com.backend.project.infrastructure.entity.BatchEntity;
import org.springframework.stereotype.Component;

@Component
public class BatchMapper {
    public BatchEntity toEntity(BatchRequestDTO dto) {
        return new BatchEntity(
                null,
                java.time.LocalDateTime.now().toString(),
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
