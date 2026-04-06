package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID> {

    @Query("SELECT a FROM AssetEntity a WHERE a.symbol = :symbol AND a.userId.id = :userId")
    AssetEntity findBySymbolAndUserId(@Param("symbol") String symbol, @Param("userId") UUID userId);

    @Query("SELECT COUNT(a) > 0 FROM AssetEntity a WHERE a.symbol = :symbol AND a.userId.id = :userId")
    boolean existsBySymbolAndUserId(@Param("symbol") String symbol, @Param("userId") UUID userId);

    @Query("SELECT a FROM AssetEntity a WHERE a.userId.id = :userId")
    List<AssetEntity> findAllByUserId(@Param("userId") UUID userId);

    @Query("SELECT SUM(a.quantity) FROM AssetEntity a WHERE a.userId.id = :userId")
    BigDecimal getQuantityByUserId(UUID userId);

    @Query("SELECT SUM(a.quantity) FROM AssetEntity a WHERE a.userId.id = :userId AND a.symbol = :symbol")
    Long getAssetQuantityByUserIdAndSymbol(UUID userId, String symbol);


    @Query("""
            SELECT
                ((:marketPrice - a.avgPrice) / a.avgPrice) * 100
            FROM AssetEntity a
            WHERE a.fkUser = :userId
            """)
    BigDecimal getUserProfitPercetagem();

    @Query
            ("""

                    SELECT
    
          (a.quantity * :marketPrice) - (a.quantity * a.avgPrice),
          ((:marketPrice - a.avgPrice) / a.avgPrice) * 100
      FROM Asset a
      WHERE a.fkUser = :userId
    """)
    BigDecimal getUserProfitValue(UUID userId, BigDecimal marketPrice);

}
