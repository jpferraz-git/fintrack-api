package com.backend.project.interfaces.dto.user;


import com.backend.project.domain.repository.RoleRepository;
import com.backend.project.infrastructure.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    private final RoleRepository roleRepository;

    public UserMapper(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserEntity toEntity(UserRequestDTO dto){
        return new UserEntity(
                null,
                dto.name(),
                dto.email(),
                dto.password(),
                roleRepository.findByName(dto.role()),
                null,
                null
        );
    }

    public UserResponseDTO toResponse(UserEntity user){
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }

}
