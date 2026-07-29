package com.backend.project.application.service;


import com.backend.project.application.Result;
import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.batch.BatchMapper;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchMapper batchMapper;
    private final UserRepository userRepository;

    public BatchService(BatchRepository batchRepository, BatchMapper batchMapper, UserRepository userRepository) {
        this.batchRepository = batchRepository;
        this.batchMapper = batchMapper;
        this.userRepository = userRepository;
    }

    public Result<BatchResponseDTO> create(BatchRequestDTO batch) {
        try {
            BatchEntity entity = batchMapper.toEntity(batch);
            entity.setUserId(getAuthenticatedUser());
            BatchEntity saved = batchRepository.create(entity);
            return Result.ok(batchMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Page<BatchResponseDTO> findAll(Pageable pageable) {
        return batchRepository.findAllByUserId(getAuthenticatedUserId(), pageable)
                .map(batchMapper::toResponse);
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
