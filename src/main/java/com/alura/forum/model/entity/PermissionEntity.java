package com.alura.forum.model.entity;

import com.alura.forum.model.enums.PermissionEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Entity representing a permission in the system.")
@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionEntity {

    @Schema(description = "Unique identifier for the permission.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "The specific permission assigned, represented as an enum.",
            example = "READ_PRIVILEGES")
    @Column(name = "permission", unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;

    public PermissionEntity(PermissionEnum permission) {
        this.permission = permission;
    }
}