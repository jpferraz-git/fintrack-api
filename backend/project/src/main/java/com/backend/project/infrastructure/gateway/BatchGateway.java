package com.backend.project.infrastructure.gateway;


import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.exception.BatchAlreadyExistsException;
import com.backend.project.exception.BatchNotFoundException;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.springdata.BatchJpaRepository;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Component
public class BatchGateway implements BatchRepository {

    private final BatchJpaRepository jpaRepository;

    public BatchGateway(BatchJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public BatchEntity create(BatchEntity batch) {
        BatchEntity existing = jpaRepository.findByFileName(batch.getFileName());
        if (existing != null) {
            throw new BatchAlreadyExistsException(batch.getFileName());
        }
        return jpaRepository.save(batch);
    }

    @Override
    public BatchEntity getReferenceById(UUID id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new BatchNotFoundException(id));
    }

    @Override
    public BatchEntity findByFileName(String fileName) {
        BatchEntity batch = jpaRepository.findByFileName(fileName);
        if (batch == null) {
            throw new BatchNotFoundException(fileName);
        }
        return batch;
    }


    @Override
    public Page<BatchEntity> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }
}
