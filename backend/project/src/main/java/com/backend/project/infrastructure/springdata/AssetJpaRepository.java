package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID> {

    @Query("SELECT a FROM AssetEntity a WHERE a.ticker = :ticker")
    AssetEntity findByTicker(String ticker);

    boolean existsByTicker(String ticker);

}
