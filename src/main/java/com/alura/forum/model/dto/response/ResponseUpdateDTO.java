package com.alura.forum.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for updating an existing response.")
public record ResponseUpdateDTO(

        @Schema(description = "The updated content of the response message.",
                example = "This is an updated response to the topic.")
        String message,

        @Schema(description = "Indicates whether this response is marked as the solution to the topic.",
                example = "true")
        String solution) {
}