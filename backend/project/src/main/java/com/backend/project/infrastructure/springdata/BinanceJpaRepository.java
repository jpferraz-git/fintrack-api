package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.BinancePriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BinanceJpaRepository extends JpaRepository<BinancePriceEntity, Long> {
}
