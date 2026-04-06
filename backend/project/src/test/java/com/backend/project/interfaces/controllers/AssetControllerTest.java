package com.backend.project.interfaces.controllers;

import com.backend.project.application.Result;
import com.backend.project.application.service.AssetService;
import com.backend.project.exception.GlobalExceptionHandler;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class AssetControllerTest {

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AssetService assetService;

    @BeforeEach
    void setup() {
        AssetController assetController = new AssetController(assetService);
        mockMvc = MockMvcBuilders.standaloneSetup(assetController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void createShouldReturnConflictWhenAssetAlreadyExists() throws Exception {
        AssetRequestDTO request = new AssetRequestDTO(
                UUID.randomUUID(),
                "BTC",
                "CRYPTO",
                new BigDecimal("10.00000000"),
                new BigDecimal("65000.00"),
                new BigDecimal("63000.00")
        );

        when(assetService.create(any(AssetRequestDTO.class)))
                .thenReturn(Result.fail("Asset with symbol 'BTC' already exists."));

        mockMvc.perform(post("/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with symbol 'BTC' already exists."));
    }

    @Test
    void updateShouldReturnNotFoundWhenAssetDoesNotExist() throws Exception {
        AssetRequestDTO request = new AssetRequestDTO(
                UUID.randomUUID(),
                "XYZ",
                "CRYPTO",
                new BigDecimal("1.00000000"),
                new BigDecimal("1.00"),
                new BigDecimal("1.00")
        );

        when(assetService.update(any(String.class), any(AssetRequestDTO.class)))
                .thenReturn(Result.fail("Asset with symbol 'XYZ' does not exist."));

        mockMvc.perform(put("/asset/XYZ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with symbol 'XYZ' does not exist."));
    }

    @Test
    void deleteShouldReturnNotFoundWhenAssetDoesNotExist() throws Exception {
        when(assetService.deleteBySymbol("XYZ"))
                .thenReturn(Result.fail("Asset with symbol 'XYZ' does not exist."));

        mockMvc.perform(delete("/asset").param("symbol", "XYZ"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with symbol 'XYZ' does not exist."));
    }
}

