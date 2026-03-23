package com.backend.project.application.service;


import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.infrastructure.entity.BatchEntity;
import com.backend.project.interfaces.dto.batch.BatchMapper;
import com.backend.project.interfaces.dto.batch.BatchRequestDTO;
import com.backend.project.interfaces.dto.batch.BatchResponseDTO;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

    private final BatchRepository batchRepository;
    private final BatchMapper batchMapper;

    public BatchService(BatchRepository batchRepository, BatchMapper batchMapper) {
        this.batchRepository = batchRepository;
        this.batchMapper = batchMapper;
    }

    public BatchResponseDTO create(BatchRequestDTO batch) {
        BatchEntity saved = batchRepository.create(
                batchMapper.toEntity(batchMapper.toModel(batch))
        );

        return batchMapper.toResponse(saved);
    }
}
