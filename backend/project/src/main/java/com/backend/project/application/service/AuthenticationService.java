package com.backend.project.application.service;

import com.backend.project.application.Result;
import com.backend.project.domain.model.Role;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
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
        Object principal = auth.getPrincipal();
        if (!(principal instanceof UserEntity user)) {
            throw new IllegalStateException("Authenticated principal is not a valid user.");
        }
        var token = tokenService.generateToken(user);

        return new LoginResponseDTO(
                token,
                new LoginResponseDTO.LoginUserResponseDTO(
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getUpdatedAt()
                )
        );
    }
    
    public Result<String> register(RegisterDTO dto){
        if (this.userRepository.existsByEmail(dto.email())) {
            return Result.fail("Email '" + dto.email() + "' is already in use.");
        }

        try {
            String encryptedPassword = new BCryptPasswordEncoder(12).encode(dto.password());
            Role role = dto.role() != null ? dto.role() : Role.USER;
            UserEntity newUser = new UserEntity(
                    dto.email(),
                    encryptedPassword,
                    roleRepository.findByName(role)
            );
            newUser.setName(resolveName(dto.name(), dto.email()));
            this.userRepository.create(newUser);
            return Result.ok("User registered successfully");
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    private String resolveName(String name, String email) {
        if (name != null && !name.isBlank()) {
            return name.trim();
        }
        return email;
    }
    
}
