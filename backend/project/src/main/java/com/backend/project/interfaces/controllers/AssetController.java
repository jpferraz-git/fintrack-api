package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.AssetService;
import com.backend.project.application.Result;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import com.backend.project.interfaces.swagger.AssetControllerSwagger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/asset")
public class AssetController implements AssetControllerSwagger {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AssetRequestDTO asset) {
        Result<AssetResponseDTO> result = assetService.create(asset);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @PutMapping("/{ticker}")
    public ResponseEntity<?> update(@PathVariable String ticker, @RequestBody AssetRequestDTO asset) {
        Result<AssetResponseDTO> result = assetService.update(ticker, asset);
        if (result.isOk()) {
            return ResponseEntity.ok(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteByTicker(@RequestParam String ticker) {
        Result<Void> result = assetService.deleteByTicker(ticker);
        if (result.isOk()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

    private HttpStatus resolveStatus(String message) {
        if (message == null || message.isBlank()) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        String normalized = message.toLowerCase();
        if (normalized.contains("not found") || normalized.contains("does not exist")) {
            return HttpStatus.NOT_FOUND;
        }
        if (normalized.contains("already exists") || normalized.contains("already in use")) {
            return HttpStatus.CONFLICT;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
