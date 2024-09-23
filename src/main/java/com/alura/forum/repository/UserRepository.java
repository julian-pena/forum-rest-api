package com.alura.forum.repository;

import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByEmail(String email);

    Optional<UserEntity> findUserEntityByEmail(String email);

}

