package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.TransactionEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TransactionRepository {

    TransactionEntity create(TransactionEntity transaction);
    Page<TransactionEntity> findAllByUserId(UUID userId, Pageable pageable);
}
