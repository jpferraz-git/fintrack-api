package com.backend.project.application.service;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.exception.UserAlreadyExistsException;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.user.UserMapper;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import org.springframework.stereotype.Service;


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
}
