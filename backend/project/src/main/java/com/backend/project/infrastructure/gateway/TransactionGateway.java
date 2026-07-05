package com.backend.project.infrastructure.gateway;


import com.backend.project.domain.repository.TransactionRepository;
import com.backend.project.exception.TransactionAlreadyExistsException;
import com.backend.project.infrastructure.entity.TransactionEntity;
import com.backend.project.infrastructure.springdata.TransactionJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Component
public class TransactionGateway implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionGateway(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TransactionEntity create(TransactionEntity transaction) {
        try {
            return jpaRepository.save(transaction);
        } catch (DataIntegrityViolationException ex) {
            throw new TransactionAlreadyExistsException("transaction");
        }
    }

    @Override
    public Page<TransactionEntity> findAllByUserId(UUID userId, Pageable pageable) {
        return jpaRepository.findAllByUserId(userId, pageable);
    }
}
