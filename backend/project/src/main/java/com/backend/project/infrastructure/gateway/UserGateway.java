package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.exception.UserAlreadyExistsException;
import com.backend.project.exception.UserNotFoundException;
import com.backend.project.infrastructure.entity.UserEntity;
import com.backend.project.infrastructure.springdata.UserJpaRepository;
import org.springframework.dao.DataIntegrityViolationException;
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
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public UserEntity updateUserByEmail(String email, UserEntity user) {
        UserEntity existingUser = jpaRepository.findByEmail(email);
        if (existingUser == null) {
            throw new UserNotFoundException(email);
        }
        if (user.getUserId() == null) {
            user.setUserId(existingUser.getUserId());
        }
        return updateUser(user);
    }

    @Override
    public UserEntity updateUser(UserEntity user) {
        if (user.getUserId() == null || !jpaRepository.existsById(user.getUserId())) {
            throw new UserNotFoundException(user.getEmail());
        }

        try {
            return jpaRepository.save(user);
        } catch (DataIntegrityViolationException ex) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
    }

    @Override
    public void deleteByEmail(String email) {
        jpaRepository.delete(findByEmail(email));
    }

    @Override
    public UserEntity getReferenceById(UUID id) {
        return jpaRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public UserEntity create(UserEntity user) {
        if (jpaRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException(user.getEmail());
        }
        return jpaRepository.save(user);
    }
}
