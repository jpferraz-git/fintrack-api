package com.backend.project.application.service;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserModel;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void criar(UserModel user) {
        userRepository.create(user);
    }
}
