package com.alura.forum.config;

import com.alura.forum.model.entity.PermissionEntity;
import com.alura.forum.model.entity.RoleEntity;
import com.alura.forum.model.enums.PermissionEnum;
import com.alura.forum.model.enums.RoleEnum;
import com.alura.forum.repository.PermissionRepository;
import com.alura.forum.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class RolesPermissionsInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Autowired
    public RolesPermissionsInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void init() {
        // Initialize Permissions
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (!permissionRepository.existsByPermission(permissionEnum)) {
                permissionRepository.save(new PermissionEntity(permissionEnum));
            }
        }

        // Initialize Roles and their Permissions
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepository.existsByRole(roleEnum)) {
                Set<PermissionEntity> permissions = roleEnum.getPermissionEnums().stream()
                        .map(permissionEnum -> permissionRepository.findByPermission(permissionEnum)
                                .orElseThrow(() -> new IllegalStateException("Permission not found: " + permissionEnum)))
                        .collect(Collectors.toSet());

                // Create the role entity and save it. The cascade setting should handle permissions.
                RoleEntity roleEntity = new RoleEntity(roleEnum, permissions);
                roleRepository.save(roleEntity);
            }
        }
    }
}

