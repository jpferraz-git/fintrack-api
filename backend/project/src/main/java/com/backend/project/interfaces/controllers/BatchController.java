package com.backend.project.interfaces.controllers;


import com.backend.project.application.Result;
import com.backend.project.application.service.BatchService;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;

@RestController
@RequestMapping("/batch")
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
    public ResponseEntity<?> create(@RequestBody BatchRequestDTO batch){
        Result<BatchResponseDTO> result = batchService.create(batch);
        if (result.isOk()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(result);
    }

}
