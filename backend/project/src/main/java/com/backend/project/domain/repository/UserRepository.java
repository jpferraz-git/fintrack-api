package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.UserEntity;

import java.util.List;

public interface UserRepository {
    UserEntity create(UserEntity user);
    List<UserEntity> findAll();
}
