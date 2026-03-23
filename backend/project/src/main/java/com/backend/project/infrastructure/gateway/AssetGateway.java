package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.springdata.AssetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AssetGateway implements AssetRepository {

    private final AssetJpaRepository jpaRepository;

    @Override
    public AssetEntity create(AssetEntity asset) {
        return jpaRepository.save(asset);
    }
}
