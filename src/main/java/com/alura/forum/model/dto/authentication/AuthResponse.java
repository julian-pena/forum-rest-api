package com.alura.forum.model.dto.authentication;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonPropertyOrder({"username", "message", "jwt", "status"})
@Schema(description = "Response object for a successful authentication, containing the user's details and a JWT token.")
public record AuthResponse(
        @Schema(description = "The username of the authenticated user, which must be an email address.",
                example = "johndoe@example.com")
        String username,

        @Schema(description = "A message indicating the success of the authentication process.",
                example = "Authentication successful.")
        String message,

        @Schema(description = "The JWT token provided to the authenticated user for accessing protected resources.",
                example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        String jwt,

        @Schema(description = "Indicates whether the authentication was successful or not.",
                example = "true")
        boolean status) {
}
