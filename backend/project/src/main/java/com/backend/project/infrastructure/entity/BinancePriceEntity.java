package com.backend.project.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.Instant;


@Entity
@Table(name = "binance_prices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BinancePriceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String symbol;
    private BigDecimal price;

    @CreationTimestamp
    private Instant created_at;

    @UpdateTimestamp
    private Instant updated_at;

    public BinancePriceEntity(String symbol, BigDecimal price) {
        this.symbol = symbol;
        this.price = price;
    }
}
