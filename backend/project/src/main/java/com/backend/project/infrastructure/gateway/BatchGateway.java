package com.backend.project.infrastructure.gateway;


import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.springdata.BatchJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class BatchGateway implements BatchRepository {

    private final BatchJpaRepository jpaRepository;

    public BatchGateway(BatchJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BatchEntity create(BatchEntity batch) {
        return jpaRepository.save(batch);
    }

    @Override
    public BatchEntity getReferenceById(UUID id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public List<BatchEntity> findAll() {
        return jpaRepository.findAll();
    }
}
