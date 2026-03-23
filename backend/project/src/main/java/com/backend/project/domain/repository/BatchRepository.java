package com.backend.project.domain.repository;


import com.backend.project.infrastructure.entity.BatchEntity;

public interface BatchRepository {

    BatchEntity create(BatchEntity batch);
}
