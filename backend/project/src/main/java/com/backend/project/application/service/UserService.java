package com.backend.project.application.service;

import com.backend.project.domain.model.UserModel;
import com.backend.project.domain.repository.UserRepository;
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
