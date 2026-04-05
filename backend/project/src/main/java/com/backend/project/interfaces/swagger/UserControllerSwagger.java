package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "User", description = "Operations for managing users")
public interface UserControllerSwagger {

    @Operation(summary = "List all users", description = "Retrieves all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDTO.class))))
    })
    ResponseEntity<List<UserResponseDTO>> findAll();

    @Operation(summary = "Create a user", description = "Creates a new user from the provided payload")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    ResponseEntity<?> createUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User payload to create",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            UserRequestDTO user);

    @Operation(summary = "Update authenticated user", description = "Updates the authenticated user without requiring email in the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    ResponseEntity<?> updateUser(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            UserRequestDTO user);

    @Operation(summary = "Update a user by email", description = "Updates an existing user using email in the path")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(schema = @Schema(implementation = UserResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    ResponseEntity<?> updateUserByEmail(
            @Parameter(description = "Email used to identify the user", example = "user@example.com", required = true)
            String email,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated user payload",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserRequestDTO.class)))
            UserRequestDTO user);

    @Operation(summary = "Delete a user", description = "Deletes a user by email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    ResponseEntity<?> deleteUser(
            @Parameter(description = "Email of the user to delete", example = "user@example.com", required = true)
            String email);
}

