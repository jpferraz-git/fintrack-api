package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.UserEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface UserRepository {

    UserEntity create(UserEntity user);

    UserEntity getReferenceById(UUID id);

    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);

    UserEntity updateUserByEmail(String email, UserEntity user);

    UserEntity updateUser(UserEntity user);

    void deleteByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);
}
