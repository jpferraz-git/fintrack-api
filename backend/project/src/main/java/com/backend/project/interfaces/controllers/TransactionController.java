package com.backend.project.interfaces.controllers;

import static com.backend.project.interfaces.controllers.utils.Normalizer.errorResponse;



import com.backend.project.application.Result;
import com.backend.project.application.service.TransactionService;
import com.backend.project.interfaces.dto.transaction.TransactionRequestDTO;
import com.backend.project.interfaces.dto.transaction.TransactionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.backend.project.interfaces.controllers.utils.Normalizer.resolveStatus;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> findAll() {
        return ResponseEntity.ok(transactionService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody TransactionRequestDTO dto) {
        Result<TransactionResponseDTO> result = transactionService.create(dto);
        if (result.isOk()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(result.getValue());
        }
        return ResponseEntity.status(resolveStatus(result.getMessage())).body(errorResponse(result.getMessage()));
    }

}
