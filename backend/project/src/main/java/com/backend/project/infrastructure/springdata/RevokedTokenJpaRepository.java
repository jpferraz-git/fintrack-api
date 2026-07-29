package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.RevokedTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RevokedTokenJpaRepository extends JpaRepository<RevokedTokenEntity, String> {
}
