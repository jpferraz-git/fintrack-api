package com.backend.project.infrastructure.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter

public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="user_id", updatable = false, nullable = false)
    private UUID userId;
    private String name;
    @Email
    private String email;
    private String password;
    private Instant createdAt;
    private Instant updatedAt;
}
