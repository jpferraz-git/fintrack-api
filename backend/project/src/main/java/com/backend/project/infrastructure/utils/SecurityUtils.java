package com.backend.project.infrastructure.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class SecurityUtils {

    public String passwordEncoder(String password) {
         return new BCryptPasswordEncoder(12).encode(password);
    }

}
