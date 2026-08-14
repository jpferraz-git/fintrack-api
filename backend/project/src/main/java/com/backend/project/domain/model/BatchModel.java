package com.backend.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchModel {
    private UUID id;
    private UUID userId;
    private String uploadDate;
    private String fileName;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;
}
