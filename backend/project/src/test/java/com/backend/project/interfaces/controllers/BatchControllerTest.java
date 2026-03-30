package com.backend.project.interfaces.controllers;

import com.backend.project.application.Result;
import com.backend.project.application.service.BatchService;
import com.backend.project.exception.GlobalExceptionHandler;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
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
class BatchControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private BatchService batchService;

    @BeforeEach
    void setup() {
        BatchController batchController = new BatchController(batchService);
        mockMvc = MockMvcBuilders.standaloneSetup(batchController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createBatchShouldReturnCreated() throws Exception {
        BatchRequestDTO request = new BatchRequestDTO("input.csv", "PENDING");
        BatchResponseDTO response = new BatchResponseDTO(
                UUID.randomUUID(),
                "2026-03-30",
                "input.csv",
                "PENDING",
                Instant.now(),
                Instant.now()
        );

        when(batchService.create(any(BatchRequestDTO.class))).thenReturn(Result.ok(response));

        mockMvc.perform(post("/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fileName").value("input.csv"))
                .andExpect(jsonPath("$.status").value("PENDING"));

        verify(batchService).create(any(BatchRequestDTO.class));
    }

    @Test
    void createBatchShouldReturnConflictWhenAlreadyExists() throws Exception {
        BatchRequestDTO request = new BatchRequestDTO("input.csv", "PENDING");

        when(batchService.create(any(BatchRequestDTO.class)))
                .thenReturn(Result.fail("Batch with identifier 'input.csv' already exists."));

        mockMvc.perform(post("/batch")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Batch with identifier 'input.csv' already exists."));
    }
}


