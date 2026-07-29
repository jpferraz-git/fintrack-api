package com.backend.project.domain.repository;


import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.infrastructure.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BatchRepository {


    BatchEntity create(BatchEntity batch);
    BatchEntity getReferenceById(UUID id);
    BatchEntity findByFileName(String fileName);
    Page<BatchEntity> findAllByUserId(UUID userId, Pageable pageable);
}
