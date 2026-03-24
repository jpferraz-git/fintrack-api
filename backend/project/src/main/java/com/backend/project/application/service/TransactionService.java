package com.backend.project.application.service;


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

    public TransactionResponseDTO create(TransactionRequestDTO transaction) {
        TransactionEntity saved = transactionRepository.create(
                transactionMapper.toEntity(transactionMapper.toModel(transaction))
        );
        return transactionMapper.toResponse(saved);
    }

    public List<TransactionResponseDTO> findAll() {
        return transactionRepository.findAll().stream()
                .map(transactionMapper::toResponse)
                .toList();
    }
}
