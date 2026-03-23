package com.backend.project.application.service;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity create(UserEntity user) {
        return userRepository.create(user);
    }
}
