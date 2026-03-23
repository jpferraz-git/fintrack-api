package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

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

    public UserResponseDTO toResponse(UserModel user){
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
