package com.backend.project.domain.model;

import java.math.BigDecimal;
import java.time.Instant;

public class BinancePriceModel {

    private Long id;
    private String symbol;
    private BigDecimal price;
    private Instant created_at;
    private Instant updated_at;

    public BinancePriceModel(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }

    public BinancePriceModel(Long id, String symbol, BigDecimal price, Instant created_at, Instant updated_at) {
        this.id = id;
        this.symbol = symbol;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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
