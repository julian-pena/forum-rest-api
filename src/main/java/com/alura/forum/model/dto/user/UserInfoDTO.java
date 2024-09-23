package com.alura.forum.model.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "DTO representing user information.")
public record UserInfoDTO(

        @Schema(description = "The unique identifier of the user.",
                example = "1",
                required = true)
        Long id,

        @Schema(description = "The name of the user.",
                example = "John Doe",
                required = true)
        String name,

        @Schema(description = "The email address of the user.",
                example = "john.doe@example.com",
                required = true)
        String email,

        @Schema(description = "The date the user registered, in ISO 8601 format.",
                example = "2024-08-20T14:30:00Z",
                required = true)
        String registrationDate,

        @Schema(description = "A set of course's titles that belong to the user.",
        example = "{ \"Spring Boot Development\", \"PostgreSQL for everybody\" }")
        Set<String> courses) {
}
