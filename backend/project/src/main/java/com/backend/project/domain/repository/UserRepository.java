package com.backend.project.domain.repository;

import com.backend.project.infrastructure.entity.UserModel;

public interface UserRepository {
    void create(UserModel user);

}
