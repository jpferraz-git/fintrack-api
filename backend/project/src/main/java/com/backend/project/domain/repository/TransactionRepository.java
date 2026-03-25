package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.TransactionEntity;

import java.util.List;

public interface TransactionRepository {

    TransactionEntity create(TransactionEntity transaction);
    List<TransactionEntity> findAll();
}
