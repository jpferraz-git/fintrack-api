package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
    UserEntity create(UserEntity user);
    UserEntity getReferenceById(UUID id);
    List<UserEntity> findAll();
}
