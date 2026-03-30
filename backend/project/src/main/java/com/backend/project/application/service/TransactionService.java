package com.backend.project.application.service;


import com.backend.project.application.Result;
import com.backend.project.domain.repository.TransactionRepository;
import com.backend.project.infrastructure.entity.TransactionEntity;
import com.backend.project.interfaces.dto.transaction.TransactionMapper;
import com.backend.project.interfaces.dto.transaction.TransactionRequestDTO;
import com.backend.project.interfaces.dto.transaction.TransactionResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionService(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    public Result<TransactionResponseDTO> create(TransactionRequestDTO transaction) {
        try {
            TransactionEntity saved = transactionRepository.create(
                    transactionMapper.toEntity(transactionMapper.toModel(transaction))
            );
            return Result.ok(transactionMapper.toResponse(saved));
        } catch (Exception ex) {
            return Result.fail(ex.getMessage());
        }
    }

    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
