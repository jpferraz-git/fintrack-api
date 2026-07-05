package com.backend.project.application.service;

import com.backend.project.application.Result;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.user.UserMapper;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public Result<UserResponseDTO> create(UserRequestDTO user) {
        try {
            UserEntity saved = userMapper.toEntity(user);
            saved.setPassword(passwordEncoder.encode(saved.getPassword()));
            saved.setRole(roleRepository.findByName(user.role()));
            saved = userRepository.create(saved);
            return Result.ok(userMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<UserResponseDTO> updateUserByEmail(String email, UserRequestDTO dto){
        try {
            UserEntity user = userRepository.findByEmail(email);
            applyUpdates(user, dto);
            UserEntity updated = userRepository.updateUserByEmail(email, user);
            return Result.ok(userMapper.toResponse(updated));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<UserResponseDTO> updateUser(UserRequestDTO dto){
        try {
            String authenticatedEmail = getAuthenticatedEmail();
            UserEntity user = userRepository.findByEmail(authenticatedEmail);
            applyUpdates(user, dto);
            UserEntity updated = userRepository.updateUser(user);
            return Result.ok(userMapper.toResponse(updated));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<UserResponseDTO> findByEmail(String email){
        try {
            UserEntity user = userRepository.findByEmail(email);
            return Result.ok(userMapper.toResponse(user));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<Void> deleteByEmail(String email){
        try {
            userRepository.deleteByEmail(email);
            return Result.ok(null);
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Page<UserResponseDTO> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(userMapper::toResponse);
    }

    private void applyUpdates(UserEntity user, UserRequestDTO dto) {
        if (dto.name() != null && !dto.name().isBlank()) {
            user.setName(dto.name().trim());
        }
        if (dto.email() != null && !dto.email().isBlank()) {
            user.setEmail(dto.email().trim());
        }
        if (dto.password() != null && !dto.password().isBlank()) {
            user.setPassword(passwordEncoder.encode(dto.password()));
        }
        if (dto.role() != null) {
            user.setRole(roleRepository.findByName(dto.role()));
        }
    }

    private String getAuthenticatedEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalStateException("No authenticated user found.");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof UserEntity userEntity) {
            return userEntity.getEmail();
        }
        if (principal instanceof String principalEmail && !"anonymousUser".equalsIgnoreCase(principalEmail)) {
            return principalEmail;
        }

        throw new IllegalStateException("Unable to resolve authenticated user email.");
    }
}
