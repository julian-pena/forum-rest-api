package com.alura.forum.repository;

import com.alura.forum.model.entity.PermissionEntity;
import com.alura.forum.model.enums.PermissionEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PermissionRepository extends JpaRepository<PermissionEntity, Long> {

    Optional<PermissionEntity> findByPermission(PermissionEnum permission);

    boolean existsByPermission(PermissionEnum permission);
}
