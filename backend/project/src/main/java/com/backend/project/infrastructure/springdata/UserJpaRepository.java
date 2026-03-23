package com.backend.project.infrastructure.springdata;

import com.backend.project.domain.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserModel, UUID> {

}
