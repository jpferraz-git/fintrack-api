package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.UserModel;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserEntity toEntity(UserModel model){
        return new UserEntity(
                null,
                model.getName(),
                model.getEmail(),
                model.getPassword(),
                null,
                null
        );

    }
    public UserModel toModel(UserRequestDTO dto){
        return new UserModel(
                null,
                dto.name(),
                dto.email(),
                dto.password(),
                null,
                null
        );
    }

    public UserResponseDTO toResponse(UserEntity user){
        return new UserResponseDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
