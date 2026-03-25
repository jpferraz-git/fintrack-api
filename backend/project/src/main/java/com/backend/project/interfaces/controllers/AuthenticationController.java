package com.backend.project.interfaces.controllers;

import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.authentication.AuthenticationDTO;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PostMapping("/login")
    public ResponseEntity<String> login (@RequestBody @Valid AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        return ResponseEntity.ok("User authenticated successfully");
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid RegisterDTO dto){
        if (this.userRepository.findByEmail(dto.email()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already in use");
        }

        String encryptedPassword = new BCryptPasswordEncoder(12).encode(dto.password());
        UserEntity newUser = new UserEntity(
                dto.email(),
                encryptedPassword,
                roleRepository.findByName(dto.role().getRole())
        );
        this.userRepository.create(newUser);
        return ResponseEntity.ok().body("User registered successfully");
    }
}
