package com.backend.project.interfaces.dto.transaction;

import com.backend.project.domain.model.TransactionModel;
import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.domain.repository.BatchRepository;
import com.backend.project.domain.repository.UserRepository;
import com.backend.project.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    private final UserRepository userRepository;
    private final BatchRepository batchRepository;
    private final AssetRepository assetRepository;

    public TransactionMapper(UserRepository userRepository, BatchRepository batchRepository, AssetRepository assetRepository) {
        this.userRepository = userRepository;
        this.batchRepository = batchRepository;
        this.assetRepository = assetRepository;
    }

    public TransactionEntity toEntity(TransactionModel model){
        return new TransactionEntity(
                model.getId(),
                userRepository.getReferenceById(model.getUserId()),
                assetRepository.getReferenceById(model.getAssetId()),
                batchRepository.getReferenceById(model.getBatchId()),
                model.getOperationType(),
                model.getQuantity(),
                model.getUnitPrice(),
                null,
                null,
                null
        );
    }

    public TransactionModel toModel(TransactionRequestDTO dto){
        return new TransactionModel(
                null,
                null,
                null,
                null,
                dto.operationType(),
                dto.quantity(),
                dto.unitPrice(),
                null,
                null,
                null
        );
    }


    public TransactionResponseDTO toResponse(TransactionEntity entity){
        return new TransactionResponseDTO(
                entity.getId(),
                userRepository.getReferenceById(entity.getUserId().getId()).getId(),
                assetRepository.getReferenceById(entity.getAssetId().getId()).getId(),
                batchRepository.getReferenceById(entity.getBatchId().getId()).getId(),
                entity.getOperationType(),
                entity.getQuantity(),
                entity.getUnitPrice(),
                entity.getOperationDate(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
