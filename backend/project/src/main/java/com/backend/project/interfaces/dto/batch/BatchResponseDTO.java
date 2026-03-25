package com.backend.project.interfaces.dto.batch;

import java.time.Instant;
import java.util.UUID;

public record BatchResponseDTO (
    UUID batchId,
    String uploadDate,
    String fileName,
    String status,
    Instant createdAt,
    Instant updatedAt
){}
