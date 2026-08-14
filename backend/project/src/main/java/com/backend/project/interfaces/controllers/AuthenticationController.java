package com.backend.project.interfaces.controllers;

import static com.backend.project.interfaces.controllers.utils.Normalizer.errorResponse;


import com.backend.project.domain.utils.Result;
import com.backend.project.application.service.AuthenticationService;
import com.backend.project.interfaces.dto.authentication.AuthenticationDTO;
import com.backend.project.interfaces.dto.authentication.LoginResponseDTO;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import com.backend.project.interfaces.swagger.AuthenticationControllerSwagger;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;

@RestController
@RequestMapping("/auth")
public class AuthenticationController implements AuthenticationControllerSwagger {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<com.backend.project.interfaces.dto.authentication.LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto, jakarta.servlet.http.HttpServletResponse response) {
        var authResult = authenticationService.login(dto);
        addTokenCookies(response, authResult.accessToken(), authResult.refreshToken());
        return ResponseEntity.ok().body(authResult.loginResponse());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        Result<String> result = authenticationService.register(dto);
        if (result.isOk()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @org.springframework.web.bind.annotation.CookieValue(value = "refreshToken", required = false) String refreshToken,
            jakarta.servlet.http.HttpServletResponse response) {
        
        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse("Refresh token is missing"));
        }

        try {
            var authResult = authenticationService.refreshToken(refreshToken);
            addTokenCookies(response, authResult.accessToken(), authResult.refreshToken());
            return ResponseEntity.ok().body(authResult.loginResponse());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @org.springframework.web.bind.annotation.CookieValue(value = "accessToken", required = false) String accessToken,
            @org.springframework.web.bind.annotation.CookieValue(value = "refreshToken", required = false) String refreshToken,
            jakarta.servlet.http.HttpServletResponse response) {
        
        authenticationService.logout(accessToken, refreshToken);
        
        jakarta.servlet.http.Cookie accessCookie = new jakarta.servlet.http.Cookie("accessToken", null);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(0);
        response.addCookie(accessCookie);
        
        jakarta.servlet.http.Cookie refreshCookie = new jakarta.servlet.http.Cookie("refreshToken", null);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(0);
        response.addCookie(refreshCookie);
        
        return ResponseEntity.ok().build();
    }

    @PostMapping("/2fa/verify")
    public ResponseEntity<?> verifyMfa(@org.springframework.web.bind.annotation.RequestParam String code, java.security.Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Simulação de MFA Verification para Fase 3 (Qualidade/Hardening)
        // O ideal é usar uma lib como aerogear-otp-java e checar contra o mfaSecret do usuário logado.
        if ("123456".equals(code)) {
            return ResponseEntity.ok().body("MFA Verified Successfully");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse("Invalid MFA Code"));
    }

    private void addTokenCookies(jakarta.servlet.http.HttpServletResponse response, String accessToken, String refreshToken) {
        jakarta.servlet.http.Cookie accessCookie = new jakarta.servlet.http.Cookie("accessToken", accessToken);
        accessCookie.setHttpOnly(true);
        accessCookie.setSecure(true);
        accessCookie.setPath("/");
        accessCookie.setMaxAge(15 * 60); // 15 minutos
        response.addCookie(accessCookie);
        
        jakarta.servlet.http.Cookie refreshCookie = new jakarta.servlet.http.Cookie("refreshToken", refreshToken);
        refreshCookie.setHttpOnly(true);
        refreshCookie.setSecure(true);
        refreshCookie.setPath("/");
        refreshCookie.setMaxAge(7 * 24 * 60 * 60); // 7 dias
        response.addCookie(refreshCookie);
    }
}
