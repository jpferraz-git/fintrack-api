package com.backend.project.application.service;

import com.backend.project.application.Result;
import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.asset.AssetMapper;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import com.backend.project.interfaces.dto.binance.price.BinancePriceResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class AssetService {

    private final AssetRepository assetRepository;
    private final UserRepository userRepository;
    private final AssetMapper assetMapper;
    private final BinanceService binanceService;

    public AssetService(AssetRepository assetRepository, UserRepository userRepository, AssetMapper assetMapper, BinanceService binanceService) {
        this.assetRepository = assetRepository;
        this.userRepository = userRepository;
        this.assetMapper = assetMapper;
        this.binanceService = binanceService;
    }

    public Result<AssetResponseDTO> create(AssetRequestDTO asset){
        try {
            AssetEntity entity = assetMapper.toEntity(asset);
            entity.setUserId(getAuthenticatedUser());
            entity.setSymbol(normalizeSymbol(asset.symbol()));
            entity.setType(resolveType(asset.type()));

            AssetEntity saved = assetRepository.create(entity);
            return Result.ok(assetMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<AssetResponseDTO> update(String symbol, AssetRequestDTO asset){
        try {
            UUID userId = getAuthenticatedUserId();
            String normalizedCurrentSymbol = normalizeSymbol(symbol);
            String normalizedNewSymbol = normalizeSymbol(asset.symbol());

            AssetEntity assetEntity = assetRepository.findBySymbolAndUserId(normalizedCurrentSymbol, userId);
            assetEntity.setSymbol(normalizedNewSymbol);
            assetEntity.setType(resolveType(asset.type()));
            Result<BigDecimal> quantityResult = calculateQuantityByInvestment(normalizedNewSymbol, asset.quantity());
            if (quantityResult.isFailure()) {
                return Result.fail(quantityResult.getMessage());
            }
            assetEntity.setQuantity(quantityResult.getValue());
            assetEntity.setAvgPrice(asset.avgPrice());
            AssetEntity updated = assetRepository.update(assetEntity);
            return Result.ok(assetMapper.toResponse(updated));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<Void> deleteBySymbol(String symbol){
        try {
            assetRepository.deleteBySymbolAndUserId(normalizeSymbol(symbol), getAuthenticatedUserId());
            return Result.ok(null);
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Page<AssetResponseDTO> findAll(Pageable pageable){
        return assetRepository.findAllByUserId(getAuthenticatedUserId(), pageable)
                .map(assetMapper::toResponse);
    }

    public Result<BigDecimal> calculateQuantityByInvestment(String symbol, BigDecimal investedValue) {
        try {
            BigDecimal actualCriptoPrice = getCurrentMarketPrice(symbol);
            BigDecimal quantity = investedValue.divide(actualCriptoPrice, 18, RoundingMode.HALF_DOWN);
            return Result.ok(new BigDecimal(quantity.toPlainString()));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    @FunctionalInterface
    private interface AssetCalculation {
        BigDecimal calculate(UUID userId, String normalizedSymbol, BigDecimal marketPrice);
    }

    private Result<BigDecimal> performAssetCalculation(String symbol, AssetCalculation calculation) {
        try {
            UUID userId = getAuthenticatedUserId();
            String normalizedSymbol = normalizeSymbol(symbol);
            BigDecimal marketPrice = getCurrentMarketPrice(normalizedSymbol);
            return Result.ok(calculation.calculate(userId, normalizedSymbol, marketPrice));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<BigDecimal> calculateActualValue(String symbol) {
        return performAssetCalculation(symbol, (userId, normalizedSymbol, marketPrice) -> {
            BigDecimal userQuantity = assetRepository.getAssetQuantityByUserAndSymbol(userId, normalizedSymbol);
            return marketPrice.multiply(userQuantity);
        });
    }

    public Result<BigDecimal> calculateProfitPercentage(String symbol) {
        return performAssetCalculation(symbol, (userId, normalizedSymbol, marketPrice) -> 
            assetRepository.getUserProfitPercentage(userId, normalizedSymbol, marketPrice)
        );
    }

    public Result<BigDecimal> calculateProfitValue(String symbol) {
        return performAssetCalculation(symbol, (userId, normalizedSymbol, marketPrice) -> 
            assetRepository.getUserProfitValue(userId, normalizedSymbol, marketPrice)
        );
    }

    public Result<BigDecimal> calculateTotalProfitPercentage() {
        try {
            UUID userId = getAuthenticatedUserId();
            List<AssetEntity> userAssets = assetRepository.findAllByUserId(userId);

            if (userAssets.isEmpty()) {
                return Result.ok(BigDecimal.ZERO);
            }

            BigDecimal totalInvestedValue = BigDecimal.ZERO;
            BigDecimal totalCurrentValue = BigDecimal.ZERO;

            Map<String, BigDecimal> pricesMap = fetchPricesConcurrently(userAssets);

            for (AssetEntity asset : userAssets) {
                BigDecimal currentPrice = pricesMap.get(asset.getSymbol());
                if (currentPrice == null) continue;

                BigDecimal investedValue = asset.getQuantity().multiply(asset.getAvgPrice());
                BigDecimal currentValue = asset.getQuantity().multiply(currentPrice);

                totalInvestedValue = totalInvestedValue.add(investedValue);
                totalCurrentValue = totalCurrentValue.add(currentValue);
            }

            if (totalInvestedValue.compareTo(BigDecimal.ZERO) == 0) {
                return Result.ok(BigDecimal.ZERO);
            }

            BigDecimal totalProfitValue = totalCurrentValue.subtract(totalInvestedValue);
            return Result.ok(totalProfitValue
                    .divide(totalInvestedValue, 8, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100)));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<BigDecimal> calculateTotalProfitValue() {
        try {
            UUID userId = getAuthenticatedUserId();
            List<AssetEntity> userAssets = assetRepository.findAllByUserId(userId);

            BigDecimal totalProfitValue = BigDecimal.ZERO;
            
            Map<String, BigDecimal> pricesMap = fetchPricesConcurrently(userAssets);

            for (AssetEntity asset : userAssets) {
                BigDecimal currentPrice = pricesMap.get(asset.getSymbol());
                if (currentPrice == null) continue;

                BigDecimal positionProfit = currentPrice
                        .subtract(asset.getAvgPrice())
                        .multiply(asset.getQuantity());

                totalProfitValue = totalProfitValue.add(positionProfit);
            }

            return Result.ok(totalProfitValue);
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    private Map<String, BigDecimal> fetchPricesConcurrently(List<AssetEntity> userAssets) {
        Map<String, BigDecimal> currentPrices = new ConcurrentHashMap<>();
        List<CompletableFuture<Void>> futures = userAssets.stream()
                .map(AssetEntity::getSymbol)
                .distinct()
                .map(symbol -> CompletableFuture.runAsync(() -> {
                    try {
                        currentPrices.put(symbol, getCurrentMarketPrice(symbol));
                    } catch (Exception ignored) {
                        // If one price fails, skip it for the total calculation
                    }
                }))
                .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
        return currentPrices;
    }

    private BigDecimal getCurrentMarketPrice(String symbol) {
        String normalizedSymbol = normalizeSymbol(symbol);
        Result<BinancePriceResponseDTO> priceResult = binanceService.getPrice(normalizedSymbol);
        if (!priceResult.isOk() || priceResult.getValue() == null || priceResult.getValue().price() == null) {
            throw new IllegalStateException("Unable to fetch market price for symbol: " + normalizedSymbol);
        }

        return priceResult.getValue().price();
    }

    private String normalizeSymbol(String symbol) {
        if (symbol == null || symbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required.");
        }

        String normalizedSymbol = symbol.trim().toUpperCase().replaceAll("[^A-Z0-9]", "");
        if (normalizedSymbol.isBlank()) {
            throw new IllegalArgumentException("Symbol is required.");
        }

        return normalizedSymbol;
    }

    private String resolveType(String type) {
        if (type == null || type.isBlank()) {
            return "CRYPTO";
        }

        return type.trim().toUpperCase();
    }

    private UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    private UserEntity getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userEntity) {
            return userEntity;
        }
        if (principal instanceof String principalEmail && !"anonymousUser".equalsIgnoreCase(principalEmail)) {
            return userRepository.findByEmail(principalEmail);
        }

        throw new IllegalStateException("Unable to resolve authenticated user.");
    }
}
