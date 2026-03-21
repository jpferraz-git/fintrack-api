package com.backend.project.domain.model;

import java.time.Instant;
import java.util.UUID;


public class AssetModel {

    private UUID asset_id;
    private String ticker;
    private String asset_type;
    private String company_name;
    private Instant created_at;
    private Instant updated_at;

    public UUID getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(UUID asset_id) {
        this.asset_id = asset_id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getAsset_type() {
        return asset_type;
    }

    public void setAsset_type(String asset_type) {
        this.asset_type = asset_type;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public Instant getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Instant created_at) {
        this.created_at = created_at;
    }

    public Instant getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Instant updated_at) {
        this.updated_at = updated_at;
    }
}
