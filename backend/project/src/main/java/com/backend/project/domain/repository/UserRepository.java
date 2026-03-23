package com.backend.project.domain.repository;

import com.backend.project.domain.model.UserModel;

public interface UserRepository {
    void create(UserModel user);

}
