package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.BatchService;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public ResponseEntity<List<BatchResponseDTO>> findAll() {
        return ResponseEntity.ok(batchService.findAll());
    }

    @PostMapping
    public ResponseEntity<BatchResponseDTO> create(@RequestBody BatchRequestDTO batch){
        return ResponseEntity.status(HttpStatus.CREATED).body(batchService.create(batch));
    }
}
