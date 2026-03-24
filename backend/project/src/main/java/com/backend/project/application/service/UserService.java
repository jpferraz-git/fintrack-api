package com.backend.project.application.service;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.user.UserMapper;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserResponseDTO create(UserRequestDTO user) {
        UserEntity saved = userRepository.create(
                userMapper.toEntity(userMapper.toModel(user))
        );
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
    public List<UserResponseDTO> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::toResponse)
                .toList();
    }
}
