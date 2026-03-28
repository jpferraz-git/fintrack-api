package com.backend.project.application.service;

import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.exception.EmailAlreadyInUseException;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.authentication.AuthenticationDTO;
import com.backend.project.interfaces.dto.authentication.LoginResponseDTO;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final RoleRepository roleRepository;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
    }

    public LoginResponseDTO login(AuthenticationDTO dto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((UserEntity) auth.getPrincipal());

        return new LoginResponseDTO(token);
    }
    
    public String register(RegisterDTO dto){
        if (this.userRepository.findByEmail(dto.email()) != null) {
            throw new EmailAlreadyInUseException(dto.email());
        }
        
        String encryptedPassword = new BCryptPasswordEncoder(12).encode(dto.password());
        UserEntity newUser = new UserEntity(
                dto.email(),
                encryptedPassword,
                roleRepository.findByName(dto.role())
        );
        System.out.println(newUser);
        this.userRepository.create(newUser);
        return "User registered successfully";
    }
    
}
