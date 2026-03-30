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
        AssetRequestDTO request = new AssetRequestDTO("BTC", "CRYPTO", "Bitcoin");

        when(assetService.create(any(AssetRequestDTO.class)))
                .thenReturn(Result.fail("Asset with ticker 'BTC' already exists."));

        mockMvc.perform(post("/asset")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with ticker 'BTC' already exists."));
    }

    @Test
    void updateShouldReturnNotFoundWhenAssetDoesNotExist() throws Exception {
        AssetRequestDTO request = new AssetRequestDTO("XYZ", "CRYPTO", "Unknown Coin");

        when(assetService.update(any(String.class), any(AssetRequestDTO.class)))
                .thenReturn(Result.fail("Asset with ticker 'XYZ' does not exist."));

        mockMvc.perform(put("/asset/XYZ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with ticker 'XYZ' does not exist."));
    }

    @Test
    void deleteShouldReturnNotFoundWhenAssetDoesNotExist() throws Exception {
        when(assetService.deleteByTicker("XYZ"))
                .thenReturn(Result.fail("Asset with ticker 'XYZ' does not exist."));

        mockMvc.perform(delete("/asset").param("ticker", "XYZ"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value("FAILURE"))
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Asset with ticker 'XYZ' does not exist."));
    }
}

