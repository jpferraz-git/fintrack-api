package com.backend.project.domain.repository;


import com.backend.project.domain.model.Role;
import com.backend.project.infrastructure.entity.RoleEntity;

import java.util.List;

public interface RoleRepository {

    List<RoleEntity> findAll();

    RoleEntity findByName(Role name);

}
