package com.backend.project.domain.model;

import java.util.UUID;

public class BatchModel {

    private UUID batch_id;
    private String upload_date;
    private String file_name;
    private String status;
    private String created_at;
    private String updated_at;

    public UUID getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(UUID batch_id) {
        this.batch_id = batch_id;
    }

    public String getUpload_date() {
        return upload_date;
    }

    public void setUpload_date(String upload_date) {
        this.upload_date = upload_date;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
