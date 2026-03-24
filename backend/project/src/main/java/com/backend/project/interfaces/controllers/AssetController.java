package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.AssetService;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @GetMapping
    public ResponseEntity<List<AssetResponseDTO>> findAll() {
        return ResponseEntity.ok(assetService.findAll());
    }

    @PostMapping
    public ResponseEntity<AssetResponseDTO> create(@RequestBody AssetRequestDTO asset) {
        return ResponseEntity.ok(assetService.create(asset));
    }
}
