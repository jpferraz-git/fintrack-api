package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.exception.UserNotFoundException;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.infrastructure.springdata.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
    public UserEntity findByEmail(String email) {
        UserEntity user = jpaRepository.findByEmail(email);
        if (user == null) {
            throw new UserNotFoundException(email);
        }
        return user;
    }

    @Override
    public UserEntity update(UserEntity user) {
        return jpaRepository.save(user);
    }

    @Override
    public void deleteByEmail(String email) {
        UserEntity user = findByEmail(email);
        jpaRepository.delete(user);
    }

    @Override
    public UserEntity getReferenceById(UUID id) {
        return jpaRepository.getReferenceById(id);
    }

    @Override
    public UserEntity create(UserEntity user) {
        return jpaRepository.save(user);
    }
}
