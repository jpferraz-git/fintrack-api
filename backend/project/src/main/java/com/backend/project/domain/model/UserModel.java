package com.backend.project.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {
    private UUID id;
    private String name;
    private String email;
    private String password;
    private RoleModel role;
    private Instant createdAt;
    private Instant updatedAt;
    private int failedLoginAttempts;
    private Instant lockoutTime;
    private boolean mfaEnabled;
    private String mfaSecret;
}
