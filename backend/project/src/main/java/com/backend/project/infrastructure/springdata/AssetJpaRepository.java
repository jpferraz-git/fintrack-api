package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AssetJpaRepository extends JpaRepository<AssetEntity, UUID> {


}
