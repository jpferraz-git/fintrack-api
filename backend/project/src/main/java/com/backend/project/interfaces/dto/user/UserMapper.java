package com.backend.project.interfaces.dto.user;

import com.backend.project.domain.model.UserModel;
import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserEntity toEntity(UserModel model){
        return new UserEntity(
                null,
                model.getName(),
                model.getEmail(),
                model.getPassword(),
                roleRepository.findByName(model.getRole()),
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
                dto.role(),
                null,
                null
        );
    }

    public UserResponseDTO toResponse(UserEntity user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
