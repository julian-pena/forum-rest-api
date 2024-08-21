package com.alura.forum.model.dto.authentication;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request object for user login, containing the user's credentials.")
public record AuthLoginRequest(
        @NotBlank
        @Schema(description = "The username of the user attempting to log in.",
                example = "johndoe@gmail.com")
        String username,

        @NotBlank
        @Schema(description = "The password of the user attempting to log in.",
                example = "P@ssw0rd123!")
        String password) {
}