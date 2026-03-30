package com.backend.project.application.service;

import com.backend.project.application.Result;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.user.UserMapper;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


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
            UserEntity saved = userMapper.toEntity(userMapper.toModel(user));
            saved.setPassword(passwordEncoder.encode(saved.getPassword()));
            saved.setRole(roleRepository.findByName(user.role()));
            saved = userRepository.create(saved);
            return Result.ok(userMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Result<UserResponseDTO> update(String email, UserRequestDTO dto){
        try {
            UserEntity user = userRepository.findByEmail(email);
            user.setName(dto.name());
            user.setEmail(dto.email());
            user.setPassword(dto.password());
            UserEntity updated = userRepository.update(user);
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

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
