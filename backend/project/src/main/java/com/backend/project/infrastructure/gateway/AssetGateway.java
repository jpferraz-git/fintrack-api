package com.backend.project.infrastructure.gateway;

import com.backend.project.domain.repository.AssetRepository;
import com.backend.project.infrastructure.entity.AssetEntity;
import com.backend.project.infrastructure.springdata.AssetJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AssetGateway implements AssetRepository {

    private final AssetJpaRepository jpaRepository;

    @Override
    public AssetEntity create(AssetEntity asset) {
        return jpaRepository.save(asset);
    }

    @Override
    public AssetEntity getReferenceById(UUID uuid) {
        return jpaRepository.getReferenceById(uuid);
    }

    @Override
    public List<AssetEntity> findAll() {
        return jpaRepository.findAll();
    }
}
