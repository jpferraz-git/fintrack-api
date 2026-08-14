package com.backend.project.interfaces.swagger;

import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "Batch", description = "Endpoints for managing batches")
public interface BatchControllerSwagger {

    @Operation(summary = "List all batches", description = "Returns a paginated list of all batches")
    ResponseEntity<Page<BatchResponseDTO>> findAll(Pageable pageable);

    @Operation(summary = "Create a new batch", description = "Creates a new batch based on the provided data")
    ResponseEntity<?> create(BatchRequestDTO batch);
}
