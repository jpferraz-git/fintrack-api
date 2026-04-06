package com.backend.project.application.service;


import com.backend.project.application.Result;
import com.backend.project.domain.repository.TransactionRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.TransactionEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.transaction.TransactionMapper;
import com.backend.project.interfaces.dto.transaction.TransactionRequestDTO;
import com.backend.project.interfaces.dto.transaction.TransactionResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.transactionMapper = transactionMapper;
    }

    public Result<TransactionResponseDTO> create(TransactionRequestDTO transaction) {
        try {
            TransactionEntity entity = transactionMapper.toEntity(transactionMapper.toModel(transaction));
            entity.setUserId(getAuthenticatedUser());

            TransactionEntity saved = transactionRepository.create(entity);
            return Result.ok(transactionMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAllByUserId(getAuthenticatedUserId()).stream()
                .map(transactionMapper::toResponse)
                .toList();
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
