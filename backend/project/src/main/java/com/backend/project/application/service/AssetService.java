package com.backend.project.application.service;

import com.backend.project.application.Result;
import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.asset.AssetMapper;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
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
            AssetEntity entity = assetMapper.toEntity(assetMapper.toModel(asset));
            entity.setUserId(getAuthenticatedUser());

            AssetEntity saved = assetRepository.create(entity);
            return Result.ok(assetMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<AssetResponseDTO> update(String symbol, AssetRequestDTO asset){
        try {
            UUID userId = getAuthenticatedUserId();
            AssetEntity assetEntity = assetRepository.findBySymbolAndUserId(symbol, userId);
            assetEntity.setSymbol(asset.symbol());
            assetEntity.setType(asset.type());
            assetEntity.setQuantity(
                    calculateQuantityByInvestment(symbol, asset.quantity())
            );
            assetEntity.setAvgPrice(asset.avgPrice());
            AssetEntity updated = assetRepository.update(assetEntity);
            return Result.ok(assetMapper.toResponse(updated));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<Void> deleteBySymbol(String symbol){
        try {
            assetRepository.deleteBySymbolAndUserId(symbol, getAuthenticatedUserId());
            return Result.ok(null);
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public List<AssetResponseDTO> findAll(){
        return assetRepository.findAllByUserId(getAuthenticatedUserId()).stream()
                .map(assetMapper::toResponse)
                .toList();
    }

    public BigDecimal calculateQuantityByInvestment(String symbol, BigDecimal investedValue) {
        UUID userId = getAuthenticatedUserId();
        BigDecimal actualCriptoPrice = binanceService.getPrice(symbol).getValue().price();
        BigDecimal quantity = investedValue.divide(actualCriptoPrice, 18, RoundingMode.HALF_DOWN);
        return new BigDecimal(quantity.toPlainString());
    }

    public BigDecimal calculateActualValue(String symbol) {
        UUID userId = getAuthenticatedUserId();
        BigDecimal actualCriptoPrice = binanceService.getPrice(symbol).getValue().price();
        BigDecimal userQuantity = assetRepository.getQuantityByUser(userId);
        return actualCriptoPrice.multiply(userQuantity);
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
