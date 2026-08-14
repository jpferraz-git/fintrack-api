package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.transaction.TransactionRequestDTO;
import com.backend.project.interfaces.dto.transaction.TransactionResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Transaction", description = "Endpoints for managing transactions")
public interface TransactionControllerSwagger {

    @Operation(summary = "List all transactions", description = "Returns a paginated list of all transactions")
    ResponseEntity<Page<TransactionResponseDTO>> findAll(Pageable pageable);

    @Operation(summary = "Create a new transaction", description = "Creates a new transaction based on the provided data")
    ResponseEntity<?> create(TransactionRequestDTO dto);
}
