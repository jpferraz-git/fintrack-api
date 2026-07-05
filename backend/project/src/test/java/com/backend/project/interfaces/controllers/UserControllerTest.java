package com.backend.project.interfaces.controllers;

import com.backend.project.application.Result;
import com.backend.project.application.service.UserService;
import com.backend.project.domain.model.Role;
import com.backend.project.exception.GlobalExceptionHandler;
import com.backend.project.interfaces.dto.user.UserRequestDTO;
import com.backend.project.interfaces.dto.user.UserResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.http.MediaType;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private UserService userService;

    @BeforeEach
    void setup() {
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createUserShouldReturnCreated() throws Exception {
        UserRequestDTO request = new UserRequestDTO("Joao", "joao@test.com", "123456", Role.USER);
        UserResponseDTO response = new UserResponseDTO(
                UUID.randomUUID(),
                "Joao",
                "joao@test.com",
                Role.USER,
                Instant.now(),
                Instant.now()
        );

        when(userService.create(any(UserRequestDTO.class))).thenReturn(Result.ok(response));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Joao"))
                .andExpect(jsonPath("$.email").value("joao@test.com"));

        verify(userService).create(any(UserRequestDTO.class));
    }

    @Test
    void createUserShouldReturnConflictWhenEmailAlreadyExists() throws Exception {
        UserRequestDTO request = new UserRequestDTO("Joao", "joao@test.com", "123456", Role.USER);

        when(userService.create(any(UserRequestDTO.class)))
                .thenReturn(Result.fail("User with email 'joao@test.com' already exists."));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("User with email 'joao@test.com' already exists."));
    }
}


