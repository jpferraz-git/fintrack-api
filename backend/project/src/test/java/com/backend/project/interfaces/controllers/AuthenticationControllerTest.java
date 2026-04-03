package com.backend.project.interfaces.controllers;

import com.backend.project.application.service.AuthenticationService;
import com.backend.project.domain.model.Role;
import com.backend.project.exception.EmailAlreadyInUseException;
import com.backend.project.exception.GlobalExceptionHandler;
import com.backend.project.interfaces.dto.register.RegisterDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AuthenticationService authenticationService;

    @BeforeEach
    void setup() {
        AuthenticationController authenticationController = new AuthenticationController(authenticationService);
        mockMvc = MockMvcBuilders.standaloneSetup(authenticationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void registerShouldReturnConflictWhenEmailAlreadyInUse() throws Exception {
        RegisterDTO request = new RegisterDTO("Joao Test", "joao@test.com", "123456", Role.USER);

        when(authenticationService.register(request))
                .thenThrow(new EmailAlreadyInUseException("joao@test.com"));

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value(409))
                .andExpect(jsonPath("$.message").value("Email 'joao@test.com' is already in use."));
    }
}

