package com.backend.project.interfaces.controllers;


import com.backend.project.application.service.BatchService;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }


    @PostMapping
    public ResponseEntity<BatchResponseDTO> create(@RequestBody BatchRequestDTO batch){
        return ResponseEntity.status(HttpStatus.CREATED).body(batchService.create(batch));
    }
}
