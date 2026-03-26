package com.backend.project.application.service;

import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.RoleEntity;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.user.UserMapper;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public UserResponseDTO create(UserRequestDTO user) {
        UserEntity saved = userMapper.toEntity(userMapper.toModel(user));
        saved.setPassword(passwordEncoder.encode(saved.getPassword()));
        saved.setRole(roleRepository.findByName(user.role()));
        saved = userRepository.create(saved);
        return userMapper.toResponse(saved);
    }

    public UserResponseDTO update(String email, UserRequestDTO dto){
        UserEntity user = userRepository.findByEmail(email);
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        UserEntity updated = userRepository.update(user);
        return userMapper.toResponse(updated);
    }

    public UserResponseDTO findByEmail(String email){
        return userMapper.toResponse(userRepository.findByEmail(email));
    }

    public void deleteByEmail(String email){
        userRepository.deleteByEmail(email);
    }

    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
