package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.model.Role;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.infrastructure.entity.RoleEntity;
import com.backend.project.infrastructure.springdata.RoleJpaRepository;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class RoleGateway implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    public RoleGateway(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public void findAll() {
        roleJpaRepository.findAll();
    }

    @Override
    public RoleEntity findByName(Role name) {
        return roleJpaRepository.findByName(name);
    }
}
