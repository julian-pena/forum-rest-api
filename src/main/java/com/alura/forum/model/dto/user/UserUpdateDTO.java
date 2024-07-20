package com.alura.forum.model.dto.user;

import com.alura.forum.validation.UniqueEmail;
import jakarta.validation.constraints.Email;

import jakarta.validation.constraints.Pattern;

public record UserUpdateDTO(@Pattern(regexp = "^(?!\\s*$).+", message = "Name cannot be empty or whitespace") String name,
                            @Email(message = "Email must be in email format") @UniqueEmail String email,
                            @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                                    message = "Password must be at least 12 characters long, contain at least one uppercase letter, " +
                                            "one lowercase letter, one special character, and one number")
                            String password) {
}
