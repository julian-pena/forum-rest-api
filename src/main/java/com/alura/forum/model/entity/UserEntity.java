package com.alura.forum.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Entity representing a user in the forum.")
@Entity(name = "User")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserEntity {

    @Schema(description = "Unique identifier for the user.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the user.",
            example = "John Doe")
    @Column(name = "user_name", nullable = false)
    private String name;

    @Schema(description = "Email address of the user. Must be unique.",
            example = "john.doe@example.com")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Schema(description = "Encrypted password for the user.",
            example = "$2a$10$X1B...")
    @Column(name = "password", nullable = false)
    private String password;

    @Schema(description = "Date when the user registered.",
            example = "2024-08-01")
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    @Schema(description = "Courses that the user is enrolled in.",
            implementation = Course.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_courses",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private Set<Course> courses = new HashSet<>();

    @Schema(description = "Roles assigned to the user.",
            implementation = RoleEntity.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Schema(description = "Indicates whether the user's account is non-expired.",
            example = "true")
    @Column(name = "account_non_expired")
    private boolean isAccountNonExpired;

    @Schema(description = "Indicates whether the user's account is non-locked.",
            example = "true")
    @Column(name = "account_non_locked")
    private boolean isAccountNonLocked;

    @Schema(description = "Indicates whether the user's credentials are non-expired.",
            example = "true")
    @Column(name = "credentials_non_expired")
    private boolean isCredentialsNonExpired;

    @Schema(description = "Indicates whether the user's account is enabled.",
            example = "true")
    @Column(name = "is_enabled")
    private boolean isEnabled;
}
