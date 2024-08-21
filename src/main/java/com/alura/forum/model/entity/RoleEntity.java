package com.alura.forum.model.entity;

import com.alura.forum.model.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;
import io.swagger.v3.oas.annotations.media.Schema;


@Schema(description = "Entity representing a role with associated permissions in the system.")
@Entity
@Table(name = "roles")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleEntity {

    @Schema(description = "Unique identifier for the role.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the role, which should be unique.",
            example = "ADMIN")
    @Column(name = "role_name", nullable = false, unique = true)
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    @Schema(description = "List of permissions associated with this role.",
            implementation = PermissionEntity.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<PermissionEntity> permissionsList = new HashSet<>();

    public RoleEntity(RoleEnum roleEnum, Set<PermissionEntity> permissions) {
        this.role = roleEnum;
        this.permissionsList = permissions;
    }
}

