package com.alura.forum.model.dto.user;

import com.alura.forum.validation.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserRegistrationDTO(@NotEmpty(message = "User name can not be null nor empty") String name,
                                  @Email(message = "Email must be in email format") @NotNull(message = "Email can not be null nor empty")
                                  @UniqueEmail String email,
                                  @NotNull(message = "Password can not be null nor empty")
                                  @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{12,}$",
                                              message = "Password must be at least 12 characters long, contain at least one uppercase letter, " +
                                                      "one lowercase letter, one special character, and one number")
                                  String password) {
}
