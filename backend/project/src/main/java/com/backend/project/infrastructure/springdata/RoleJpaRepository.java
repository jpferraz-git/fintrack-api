package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleJpaRepository extends JpaRepository<RoleEntity, UUID> {

    @Query("SELECT r FROM RoleEntity r WHERE r.name = :name")
    RoleEntity findByName(String name);

}
