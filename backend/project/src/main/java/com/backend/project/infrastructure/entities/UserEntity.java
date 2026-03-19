package com.backend.project.infrastructure.entities;

import jakarta.persistence.Entity;
import org.hibernate.validator.constraints.UUID;

@Entity
public class UserEntity {

    @UUID
    private int user_id;
}
