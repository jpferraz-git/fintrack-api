package com.backend.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetModel {
    private UUID id;
    private UUID userId;
    private String symbol;
    private String type;
    private BigDecimal quantity;
    private BigDecimal avgPrice;
    private Instant createdAt;
    private Instant updatedAt;
}
