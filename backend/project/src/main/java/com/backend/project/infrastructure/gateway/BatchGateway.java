package com.backend.project.infrastructure.gateway;


import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.springdata.BatchJpaRepository;
import org.springframework.stereotype.Component;

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
}
