package com.backend.project.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "binance_24h_ticker")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Binance24hTickerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private BigDecimal priceChange;
    private BigDecimal priceChangePercent;
    private BigDecimal weightedAvgPrice;
    private BigDecimal prevClosePrice;
    private BigDecimal lastPrice;
    private BigDecimal lastQty;
    private BigDecimal bidPrice;
    private BigDecimal lowPrice;
    private BigDecimal volume;
    private BigDecimal quoteVolume;
    private Instant openTime;
    private Instant closeTime;
    private Long firstId;
    private Long lastId;
    private Long count;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    public Binance24hTickerEntity(String symbol, BigDecimal priceChange, BigDecimal priceChangePercent, BigDecimal weightedAvgPrice, BigDecimal prevClosePrice, BigDecimal lastPrice, BigDecimal lastQty, BigDecimal bidPrice, BigDecimal lowPrice, BigDecimal volume, BigDecimal quoteVolume, Instant openTime, Instant closeTime, Long firstId, Long lastId, Long count) {
        this.symbol = symbol;
        this.priceChange = priceChange;
        this.priceChangePercent = priceChangePercent;
        this.weightedAvgPrice = weightedAvgPrice;
        this.prevClosePrice = prevClosePrice;
        this.lastPrice = lastPrice;
        this.lastQty = lastQty;
        this.bidPrice = bidPrice;
        this.lowPrice = lowPrice;
        this.volume = volume;
        this.quoteVolume = quoteVolume;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.firstId = firstId;
        this.lastId = lastId;
        this.count = count;
    }
}

