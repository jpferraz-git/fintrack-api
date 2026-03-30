package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.authentication.AuthenticationDTO;
import com.backend.project.interfaces.dto.authentication.LoginResponseDTO;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Authentication", description = "Operations for user authentication and registration")
public interface AuthenticationControllerSwagger {

    @Operation(summary = "User login", description = "Authenticates a user with email and password, returning a JWT token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = LoginResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Invalid email or password", content = @Content)
    })
    ResponseEntity<LoginResponseDTO> login(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User credentials",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthenticationDTO.class)))
            AuthenticationDTO dto);

    @Operation(summary = "User registration", description = "Creates a new user account with email, password and role")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "Email already in use", content = @Content)
    })
    ResponseEntity<?> register(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = RegisterDTO.class)))
            RegisterDTO dto);
}

