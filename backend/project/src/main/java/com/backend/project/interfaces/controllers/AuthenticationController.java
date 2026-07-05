package com.backend.project.interfaces.controllers;

import static com.backend.project.interfaces.controllers.utils.Normalizer.errorResponse;


import com.backend.project.application.Result;
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
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO dto) {
        return ResponseEntity.ok().body(authenticationService.login(dto));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO dto) {
        Result<String> result = authenticationService.register(dto);
        if (result.isOk()) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }
}
