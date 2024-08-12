package com.alura.forum.model.entity;

import com.alura.forum.model.enums.PermissionEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "permissions")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "permission", unique = true, nullable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    private PermissionEnum permission;

    public PermissionEntity(PermissionEnum permission) {
        this.permission = permission;
    }
}
