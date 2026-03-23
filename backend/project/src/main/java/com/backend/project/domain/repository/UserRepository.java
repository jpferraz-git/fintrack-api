package com.backend.project.domain.repository;

import com.backend.project.domain.model.UserModel;
import com.backend.project.infrastructure.entity.UserEntity;

public interface UserRepository {
    UserEntity create(UserEntity user);
}
