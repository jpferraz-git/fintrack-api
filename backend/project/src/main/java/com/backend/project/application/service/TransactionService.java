package com.backend.project.application.service;


import com.backend.project.application.Result;
import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.domain.repository.TransactionRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.exception.AssetNotFoundException;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.entity.TransactionEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.transaction.TransactionMapper;
import com.backend.project.interfaces.dto.transaction.TransactionRequestDTO;
import com.backend.project.interfaces.dto.transaction.TransactionResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class TransactionService {

    private static final String BUY = "BUY";
    private static final String SELL = "SELL";

    private final AssetRepository assetRepository;
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(AssetRepository assetRepository, TransactionRepository transactionRepository, UserRepository userRepository, TransactionMapper transactionMapper) {
        this.assetRepository = assetRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    @Transactional
    public Result<TransactionResponseDTO> create(TransactionRequestDTO transaction) {
        try {
            UserEntity authenticatedUser = getAuthenticatedUser();

            TransactionEntity entity = transactionMapper.toEntity(transaction);
            entity.setUserId(authenticatedUser);
            entity.setSymbol(normalizeSymbol(transaction.symbol()));
            entity.setType(resolveTransactionType(transaction.type()));
            entity.setQuantity(requirePositive(transaction.quantity(), "quantity"));
            entity.setPrice(requirePositive(transaction.price(), "price"));
            synchronizeAssetPosition(authenticatedUser, entity);

            TransactionEntity saved = transactionRepository.create(entity);

            return Result.ok(transactionMapper.toResponse(saved));
        } catch (Exception ex) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return Result.fail(ex.getMessage());
        }
    }

    public Page<TransactionResponseDTO> findAll(Pageable pageable) {
        return transactionRepository.findAllByUserId(getAuthenticatedUserId(), pageable)
                .map(transactionMapper::toResponse);
    }

    private UUID getAuthenticatedUserId() {
        return getAuthenticatedUser().getId();
    }

    private void synchronizeAssetPosition(UserEntity user, TransactionEntity transaction) {
        UUID userId = user.getId();
        String symbol = transaction.getSymbol();

        AssetEntity currentAsset = findAssetByUserAndSymbol(symbol, userId);

        if (BUY.equals(transaction.getType())) {
            handleBuy(user, transaction, currentAsset);
            return;
        }

        handleSell(userId, symbol, transaction, currentAsset);
    }

    private void handleBuy(UserEntity user, TransactionEntity transaction, AssetEntity currentAsset) {
        if (currentAsset == null) {
            AssetEntity newAsset = new AssetEntity(
                    null,
                    user,
                    transaction.getSymbol(),
                    "CRYPTO",
                    transaction.getQuantity(),
                    transaction.getPrice(),
                    null,
                    null
            );

            assetRepository.create(newAsset);
            return;
        }

        BigDecimal previousQuantity = currentAsset.getQuantity();
        BigDecimal previousAvgPrice = currentAsset.getAvgPrice();

        BigDecimal updatedQuantity = previousQuantity.add(transaction.getQuantity());
        BigDecimal previousCost = previousQuantity.multiply(previousAvgPrice);
        BigDecimal incomingCost = transaction.getQuantity().multiply(transaction.getPrice());
        BigDecimal updatedAvgPrice = previousCost
                .add(incomingCost)
                .divide(updatedQuantity, 8, RoundingMode.HALF_UP);

        currentAsset.setType("CRYPTO");
        currentAsset.setQuantity(updatedQuantity);
        currentAsset.setAvgPrice(updatedAvgPrice);
        assetRepository.update(currentAsset);
    }

    private void handleSell(UUID userId, String symbol, TransactionEntity transaction, AssetEntity currentAsset) {
        if (currentAsset == null) {
            throw new IllegalArgumentException("Cannot register SELL transaction without an existing asset position.");
        }

        BigDecimal currentQuantity = currentAsset.getQuantity();
        if (currentQuantity.compareTo(transaction.getQuantity()) < 0) {
            throw new IllegalArgumentException("Cannot sell more than the current asset quantity.");
        }

        BigDecimal updatedQuantity = currentQuantity.subtract(transaction.getQuantity());
        if (updatedQuantity.compareTo(BigDecimal.ZERO) == 0) {
            assetRepository.deleteBySymbolAndUserId(symbol, userId);
            return;
        }

        currentAsset.setQuantity(updatedQuantity);
        assetRepository.update(currentAsset);
    }

    private AssetEntity findAssetByUserAndSymbol(String symbol, UUID userId) {
        try {
            return assetRepository.findBySymbolAndUserId(symbol, userId);
        } catch (AssetNotFoundException ex) {
            return null;
        }
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

    private String resolveTransactionType(String type) {
        if (type == null || type.isBlank()) {
            return BUY;
        }

        String normalizedType = type.trim().toUpperCase();
        if (!BUY.equals(normalizedType) && !SELL.equals(normalizedType)) {
            throw new IllegalArgumentException("Transaction type must be BUY or SELL.");
        }

        return normalizedType;
    }

    private BigDecimal requirePositive(BigDecimal value, String fieldName) {
        if (value == null || value.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Transaction " + fieldName + " must be greater than zero.");
        }
        return value;
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
