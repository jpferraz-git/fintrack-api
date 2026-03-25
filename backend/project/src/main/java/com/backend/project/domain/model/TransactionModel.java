package com.backend.project.domain.model;

import java.time.Instant;
import java.util.UUID;
import java.util.Date;

public class TransactionModel {

    private UUID id;
    private UUID userId;
    private UUID assetId;
    private UUID batchId;
    private String operationType;
    private int quantity;
    private double unitPrice;
    private Date operationDate;
    private Instant createdAt;
    private Instant updatedAt;

    public TransactionModel(UUID id, UUID userId, UUID assetId, UUID batchId, String operationType, int quantity, double unitPrice, Date operationDate, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.userId = userId;
        this.assetId = assetId;
        this.batchId = batchId;
        this.operationType = operationType;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.operationDate = operationDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getAssetId() {
        return assetId;
    }

    public void setAssetId(UUID assetId) {
        this.assetId = assetId;
    }

    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(Date operationDate) {
        this.operationDate = operationDate;
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
