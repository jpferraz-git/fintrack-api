package com.backend.project.infrastructure.springdata;


import com.backend.project.infrastructure.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BatchJpaRepository extends JpaRepository<BatchEntity, UUID> {

	BatchEntity findByFileName(String fileName);

	@org.springframework.data.jpa.repository.Query("SELECT b FROM BatchEntity b WHERE b.userId.id = :userId")
	org.springframework.data.domain.Page<BatchEntity> findAllByUserId(@org.springframework.data.repository.query.Param("userId") java.util.UUID userId, org.springframework.data.domain.Pageable pageable);
}
