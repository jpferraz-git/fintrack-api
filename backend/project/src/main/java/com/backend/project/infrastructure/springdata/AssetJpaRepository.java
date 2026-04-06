package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID> {

    @Query("SELECT a FROM AssetEntity a WHERE a.ticker = :ticker AND a.userId.id = :userId")
    AssetEntity findByTickerAndUserId(@Param("ticker") String ticker, @Param("userId") UUID userId);

    @Query("SELECT COUNT(a) > 0 FROM AssetEntity a WHERE a.ticker = :ticker AND a.userId.id = :userId")
    boolean existsByTickerAndUserId(@Param("ticker") String ticker, @Param("userId") UUID userId);

    @Query("SELECT a FROM AssetEntity a WHERE a.userId.id = :userId")
    List<AssetEntity> findAllByUserId(@Param("userId") UUID userId);

}
