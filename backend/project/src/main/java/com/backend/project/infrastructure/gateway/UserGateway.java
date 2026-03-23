package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.domain.model.UserModel;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.infrastructure.springdata.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserGateway implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserGateway(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public List<UserEntity> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public UserEntity create(UserEntity user) {
        return jpaRepository.save(user);
    }
}
