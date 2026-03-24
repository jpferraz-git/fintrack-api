package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.AssetService;
import com.backend.project.interfaces.dto.asset.AssetRequestDTO;
import com.backend.project.interfaces.dto.asset.AssetResponseDTO;
import com.backend.project.interfaces.swagger.AssetControllerSwagger;
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
    public ResponseEntity<AssetResponseDTO> create(@RequestBody AssetRequestDTO asset) {
        return ResponseEntity.ok(assetService.create(asset));
    }

    @PutMapping("/{ticker}")
    public ResponseEntity<AssetResponseDTO> update(@PathVariable String ticker, @RequestBody AssetRequestDTO asset) {
        return ResponseEntity.ok(assetService.update(ticker, asset));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByTicker(@RequestParam String ticker) {
        assetService.deleteByTicker(ticker);
        return ResponseEntity.noContent().build();
    }
}
