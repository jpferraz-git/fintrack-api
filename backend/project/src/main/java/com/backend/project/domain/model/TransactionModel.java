package com.backend.project.domain.model;

import java.time.Instant;
import java.util.UUID;
import java.util.Date;

public class TransactionModel {

    private UUID id;
    private UUID user_id;
    private UUID asset_id;
    private UUID batch_id;
    private String operation_type;
    private int quantity;
    private double unit_price;
    private Date operation_date;
    private Instant created_at;
    private Instant updated_at;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUser_id() {
        return user_id;
    }

    public void setUser_id(UUID user_id) {
        this.user_id = user_id;
    }

    public UUID getAsset_id() {
        return asset_id;
    }

    public void setAsset_id(UUID asset_id) {
        this.asset_id = asset_id;
    }

    public UUID getBatch_id() {
        return batch_id;
    }

    public void setBatch_id(UUID batch_id) {
        this.batch_id = batch_id;
    }

    public String getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(String operation_type) {
        this.operation_type = operation_type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(double unit_price) {
        this.unit_price = unit_price;
    }

    public Date getOperation_date() {
        return operation_date;
    }

    public void setOperation_date(Date operation_date) {
        this.operation_date = operation_date;
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
