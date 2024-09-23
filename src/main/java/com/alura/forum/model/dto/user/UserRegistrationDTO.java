package com.alura.forum.model.dto.user;

import com.alura.forum.validation.UniqueEmail;
import com.alura.forum.validation.ValidRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for registering a new user.")
public record UserRegistrationDTO(

        @Schema(description = "The name of the user.",
                example = "John Doe",
                required = true)
        @NotEmpty(message = "User name can not be null nor empty")
        String name,

        @Schema(description = "The email address of the user.",
                example = "john.doe@example.com",
                required = true)
        @Email(message = "Email must be in email format: example@domain.com")
        @NotEmpty(message = "Email can not be null nor empty")
        @UniqueEmail
        String email,

        @Schema(description = "The password for the user, which must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number.",
                example = "P@ssw0rd1234!",
                required = true)
        @NotEmpty(message = "Password can not be null nor empty")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number")
        String password,

        @Schema(description = "The role assigned to the user, which must be a valid role defined in the system.",
                example = "USER",
                required = true)
        @NotEmpty
        @ValidRole
        String role) {
}

