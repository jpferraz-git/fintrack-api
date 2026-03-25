package com.backend.project.infrastructure.gateway;


import com.backend.project.domain.repository.TransactionRepository;
import com.backend.project.infrastructure.entity.TransactionEntity;
import com.backend.project.infrastructure.springdata.TransactionJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionGateway implements TransactionRepository {

    private final TransactionJpaRepository jpaRepository;

    public TransactionGateway(TransactionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public TransactionEntity create(TransactionEntity transaction) {
        return jpaRepository.save(transaction);
    }

    @Override
    public List<TransactionEntity> findAll() {
        return jpaRepository.findAll();
    }
}
