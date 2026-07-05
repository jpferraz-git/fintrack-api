package com.backend.project.interfaces.dto.transaction;


import com.backend.project.infrastructure.entity.TransactionEntity;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {

    public TransactionEntity toEntity(TransactionRequestDTO dto) {
        return new TransactionEntity(
                null,
                null,
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
