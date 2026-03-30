package com.backend.project.domain.repository;


import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface BatchRepository {


    BatchEntity create(BatchEntity batch);
    BatchEntity getReferenceById(UUID id);
    BatchEntity findByFileName(String fileName);
    List<BatchEntity> findAll();
}
