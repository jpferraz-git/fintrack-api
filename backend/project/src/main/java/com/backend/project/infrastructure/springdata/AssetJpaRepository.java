package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID> {

    @Query("SELECT a FROM AssetEntity a WHERE a.symbol = :symbol AND a.userId.id = :userId")
    AssetEntity findBySymbolAndUserId(@Param("symbol") String symbol, @Param("userId") UUID userId);

        @Query("""
                        SELECT a
                        FROM AssetEntity a
                        WHERE a.symbol = :symbol AND a.userId.id = :userId
                        ORDER BY a.updatedAt DESC, a.createdAt DESC, a.id DESC
                        """)
        List<AssetEntity> findAllBySymbolAndUserId(@Param("symbol") String symbol, @Param("userId") UUID userId);

    @Query("SELECT COUNT(a) > 0 FROM AssetEntity a WHERE a.symbol = :symbol AND a.userId.id = :userId")
    boolean existsBySymbolAndUserId(@Param("symbol") String symbol, @Param("userId") UUID userId);

    @Query("SELECT a FROM AssetEntity a WHERE a.userId.id = :userId")
    Page<AssetEntity> findAllByUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT SUM(a.quantity) FROM AssetEntity a WHERE a.userId.id = :userId")
    BigDecimal getQuantityByUserId(@Param("userId") UUID userId);

    @Query("SELECT SUM(a.quantity) FROM AssetEntity a WHERE a.userId.id = :userId AND a.symbol = :symbol")
    BigDecimal getAssetQuantityByUserIdAndSymbol(@Param("userId") UUID userId, @Param("symbol") String symbol);


    @Query("""
            SELECT CASE
                WHEN COALESCE(SUM(a.quantity * a.avgPrice), 0) = 0 THEN 0
                ELSE (SUM((a.quantity * :marketPrice) - (a.quantity * a.avgPrice))
                    / SUM(a.quantity * a.avgPrice)) * 100
            END
            FROM AssetEntity a
            WHERE a.userId.id = :userId AND a.symbol = :symbol
            """)
    BigDecimal getUserProfitPercentage(@Param("userId") UUID userId, @Param("symbol") String symbol, @Param("marketPrice") BigDecimal marketPrice);

    @Query
            ("""
                    SELECT COALESCE(SUM((a.quantity * :marketPrice) - (a.quantity * a.avgPrice)), 0)
                    FROM AssetEntity a
                    WHERE a.userId.id = :userId AND a.symbol = :symbol
    """)
        BigDecimal getUserProfitValue(@Param("userId") UUID userId, @Param("symbol") String symbol, @Param("marketPrice") BigDecimal marketPrice);

}
