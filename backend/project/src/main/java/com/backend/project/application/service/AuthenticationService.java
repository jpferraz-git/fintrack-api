package com.backend.project.application.service;

import com.backend.project.domain.utils.Result;
import com.backend.project.domain.model.Role;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.interfaces.dto.authentication.AuthenticationDTO;
import com.backend.project.interfaces.dto.authentication.LoginResponseDTO;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.backend.project.interfaces.dto.authentication.AuthResultDTO;
import org.springframework.security.core.AuthenticationException;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(UserRepository userRepository, AuthenticationManager authenticationManager, TokenService tokenService, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResultDTO login(AuthenticationDTO dto){
        UserEntity userEntity = (UserEntity) userRepository.findByEmail(dto.email());
        if (userEntity != null) {
            if (!userEntity.isAccountNonLocked()) {
                throw new IllegalStateException("Account is temporarily locked. Try again later.");
            }
        }

        try {
            var usernamePassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
            var auth = this.authenticationManager.authenticate(usernamePassword);
            Object principal = auth.getPrincipal();
            if (!(principal instanceof UserEntity user)) {
                throw new IllegalStateException("Authenticated principal is not a valid user.");
            }
            
            // Sucesso: resetar lockout
            if (userEntity != null) {
                userEntity.setFailedLoginAttempts(0);
                userEntity.setLockoutTime(null);
                userRepository.update(userEntity);
            }

            var accessToken = tokenService.generateToken(user);
            var refreshToken = tokenService.generateRefreshToken(user);

            return new AuthResultDTO(
                    accessToken,
                    refreshToken,
                    new LoginResponseDTO(
                            new LoginResponseDTO.LoginUserResponseDTO(
                                    user.getName(),
                                    user.getEmail(),
                                    user.getCreatedAt(),
                                    user.getUpdatedAt()
                            )
                    )
            );
        } catch (AuthenticationException e) {
            if (userEntity != null) {
                userEntity.setFailedLoginAttempts(userEntity.getFailedLoginAttempts() + 1);
                if (userEntity.getFailedLoginAttempts() >= 5) {
                    userEntity.setLockoutTime(java.time.Instant.now().plus(java.time.Duration.ofMinutes(15)));
                }
                userRepository.update(userEntity);
            }
            throw e;
        }
    }
    
    public Result<String> register(RegisterDTO dto){
        if (this.userRepository.existsByEmail(dto.email())) {
            return Result.fail("Email '" + dto.email() + "' is already in use.");
        }

        try {
            String encryptedPassword = passwordEncoder.encode(dto.password());
            Role role = Role.USER;
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
    public AuthResultDTO refreshToken(String refreshToken) {
        String email = tokenService.validateToken(refreshToken);
        if (email == null || tokenService.isTokenRevoked(refreshToken)) {
            throw new IllegalStateException("Invalid or expired refresh token");
        }

        UserEntity userEntity = (UserEntity) userRepository.findByEmail(email);
        if (userEntity == null || !userEntity.isAccountNonLocked()) {
            throw new IllegalStateException("User not found or account locked");
        }

        var newAccessToken = tokenService.generateToken(userEntity);
        var newRefreshToken = tokenService.generateRefreshToken(userEntity);

        tokenService.revokeToken(refreshToken);

        return new AuthResultDTO(
                newAccessToken,
                newRefreshToken,
                new LoginResponseDTO(
                        new LoginResponseDTO.LoginUserResponseDTO(
                                userEntity.getName(),
                                userEntity.getEmail(),
                                userEntity.getCreatedAt(),
                                userEntity.getUpdatedAt()
                        )
                )
        );
    }

    public void logout(String accessToken, String refreshToken) {
        if (accessToken != null) {
            tokenService.revokeToken(accessToken);
        }
        if (refreshToken != null) {
            tokenService.revokeToken(refreshToken);
        }
    }
}
