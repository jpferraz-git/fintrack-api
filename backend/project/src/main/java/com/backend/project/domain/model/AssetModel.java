package com.backend.project.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;


public class AssetModel {

    private UUID assetId;
    private UUID userId;
    private String symbol;
    private String type;
    private BigDecimal quantity;
    private BigDecimal avgPrice;
    private Instant createdAt;
    private Instant updatedAt;

    public AssetModel(UUID assetId, UUID userId, String symbol, String type, BigDecimal quantity, BigDecimal avgPrice, Instant createdAt, Instant updatedAt) {
        this.assetId = assetId;
        this.userId = userId;
        this.symbol = symbol;
        this.type = type;
        this.quantity = quantity;
        this.avgPrice = avgPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
