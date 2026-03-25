package com.backend.project.infrastructure.springdata;


import com.backend.project.infrastructure.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchJpaRepository extends JpaRepository<BatchEntity, UUID> {
}
