package com.backend.project.infrastructure.springdata;

import com.backend.project.infrastructure.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface TransactionJpaRepository extends JpaRepository<TransactionEntity, UUID> {

	@Query("SELECT t FROM TransactionEntity t WHERE t.userId.id = :userId")
	List<TransactionEntity> findAllByUserId(@Param("userId") UUID userId);

}
