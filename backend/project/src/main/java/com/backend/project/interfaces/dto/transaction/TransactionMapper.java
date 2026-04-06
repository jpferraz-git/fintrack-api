package com.backend.project.interfaces.dto.transaction;

import com.backend.project.domain.model.TransactionModel;
import com.backend.project.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionEntity toEntity(TransactionModel model){
        return new TransactionEntity(
                model.getId(),
                null,
                model.getSymbol(),
                model.getType(),
                model.getQuantity(),
                model.getPrice(),
                null,
                null
        );
    }

    public TransactionModel toModel(TransactionRequestDTO dto){
        return new TransactionModel(
                null,
                dto.fkUser(),
                dto.symbol(),
                dto.type(),
                dto.quantity(),
                dto.price(),
                null,
                null
        );
    }


    public TransactionResponseDTO toResponse(TransactionEntity entity){
        return new TransactionResponseDTO(
                entity.getId(),
                entity.getUserId() != null ? entity.getUserId().getId() : null,
                entity.getSymbol(),
                entity.getType(),
                entity.getQuantity(),
                entity.getPrice(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
