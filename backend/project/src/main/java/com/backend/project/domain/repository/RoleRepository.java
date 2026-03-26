package com.backend.project.domain.repository;


import com.backend.project.domain.model.Role;
import com.backend.project.infrastructure.entity.RoleEntity;

public interface RoleRepository {

    void findAll();

    RoleEntity findByName(Role name);

}
