package com.backend.project.infrastructure.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "revoked_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevokedTokenEntity {

    @Id
    @Column(name = "token", updatable = false, nullable = false, length = 1000)
    private String token;

    @Column(name = "revoked_at", nullable = false)
    private Instant revokedAt;
}
