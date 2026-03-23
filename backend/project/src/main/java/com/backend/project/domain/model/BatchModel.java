package com.backend.project.domain.model;

import java.time.Instant;
import java.util.UUID;

public class BatchModel {

    private UUID batchId;
    private String uploadDate;
    private String fileName;
    private String status;
    private Instant createdAt;
    private Instant updatedAt;

    public BatchModel(UUID batchId, String uploadDate, String fileName, String status, Instant createdAt, Instant updatedAt) {
        this.batchId = batchId;
        this.uploadDate = uploadDate;
        this.fileName = fileName;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getBatchId() {
        return batchId;
    }

    public void setBatchId(UUID batchId) {
        this.batchId = batchId;
    }

    public String getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(String uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
