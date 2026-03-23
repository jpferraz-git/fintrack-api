package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.AssetService;
import com.backend.project.infrastructure.entity.AssetEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/asset")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    public ResponseEntity<AssetEntity> create(@RequestBody AssetEntity asset) {
        return ResponseEntity.ok(assetService.create(asset));
    }
}
