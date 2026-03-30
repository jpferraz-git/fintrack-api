package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.UserEntity;

import java.util.List;
import java.util.UUID;

public interface UserRepository {

    UserEntity create(UserEntity user);

    UserEntity getReferenceById(UUID id);

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    UserEntity update(UserEntity user);

    void deleteByEmail(String email);

    List<UserEntity> findAll();
}
