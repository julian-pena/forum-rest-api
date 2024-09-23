package com.alura.forum.model.dto.user;

import com.alura.forum.validation.UniqueEmail;
import com.alura.forum.validation.ValidLongValue;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for updating an existing user's information.")
public record UserUpdateDTO(

        @Schema(description = "The updated name of the user.",
                example = "John Doe")
        @Pattern(regexp = "^(?!\\s*$).+", message = "Name cannot be empty or whitespace")
        String name,

        @Schema(description = "The updated email address of the user.",
                example = "john.doe@newdomain.com")
        @Email(message = "Email must be in email format")
        @UniqueEmail
        String email,

        @Schema(description = "The updated password for the user, which must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number.",
                example = "NewP@ssw0rd1234!")
        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                message = "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one special character, and one number")
        String password,
        @Schema(description = "A new course for the user, which is provided via Course ID.",
                example = "3")
        @ValidLongValue
        String newCourseId) {
}
