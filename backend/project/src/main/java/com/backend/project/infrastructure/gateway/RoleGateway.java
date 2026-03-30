package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.model.Role;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.exception.RoleNotFoundException;
import com.backend.project.infrastructure.entity.RoleEntity;
import com.backend.project.infrastructure.springdata.RoleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleGateway implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    public RoleGateway(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleJpaRepository.findAll();
    }

    @Override
    public RoleEntity findByName(Role name) {
        RoleEntity role = roleJpaRepository.findByName(name);
        if (role == null) {
            throw new RoleNotFoundException(name);
        }
        return role;
    }
}
