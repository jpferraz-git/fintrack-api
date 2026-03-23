package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.UserRepository;
import com.backend.project.domain.model.UserModel;
import com.backend.project.infrastructure.springdata.UserJpaRepository;
import org.springframework.stereotype.Component;

@Component
public class UserGateway implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserGateway(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void create(UserModel user) {
        jpaRepository.save(user);
    }
}
