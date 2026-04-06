package com.backend.project.domain.model;

import java.time.Instant;
import java.util.UUID;


public class AssetModel {

    private UUID assetId;
    private UUID userId;
    private String ticker;
    private String assetType;
    private String companyName;
    private Instant createdAt;
    private Instant updatedAt;

    public AssetModel(UUID assetId, UUID userId, String ticker, String assetType, String companyName, Instant createdAt, Instant updatedAt) {
        this.assetId = assetId;
        this.userId = userId;
        this.ticker = ticker;
        this.assetType = assetType;
        this.companyName = companyName;
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

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getAssetType() {
        return assetType;
    }

    public void setAssetType(String assetType) {
        this.assetType = assetType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
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
