package com.backend.project.application.service;


import com.backend.project.application.Result;
import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.interfaces.dto.batch.BatchMapper;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchMapper batchMapper;

    public BatchService(BatchRepository batchRepository, BatchMapper batchMapper) {
        this.batchRepository = batchRepository;
        this.batchMapper = batchMapper;
    }

    public Result<BatchResponseDTO> create(BatchRequestDTO batch) {
        try {
            BatchEntity saved = batchRepository.create(
                    batchMapper.toEntity(batch)
            );
            return Result.ok(batchMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public Page<BatchResponseDTO> findAll(Pageable pageable) {
        return batchRepository.findAll(pageable)
                .map(batchMapper::toResponse);
    }
}
