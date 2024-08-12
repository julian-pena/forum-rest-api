package com.alura.forum.repository;

import com.alura.forum.model.entity.RoleEntity;
import com.alura.forum.model.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    boolean existsByRole(RoleEnum role);

    Optional<RoleEntity> findByRole(RoleEnum role);

}
