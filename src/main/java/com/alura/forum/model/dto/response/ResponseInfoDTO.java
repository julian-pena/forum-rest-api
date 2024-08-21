package com.alura.forum.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO for displaying detailed information about a response within a topic.")
public record ResponseInfoDTO(

        @Schema(description = "Unique identifier of the response.",
                example = "123")
        Long id,

        @Schema(description = "Content of the response message.",
                example = "This is the content of the response.")
        String message,

        @Schema(description = "Title of the topic to which this response belongs.",
                example = "Understanding Java Streams")
        String topic,

        @Schema(description = "Date and time when the response was created, in ISO 8601 format.",
                example = "2024-08-20T10:30:00Z")
        String creationDate,

        @Schema(description = "Name of the user who posted the response.",
                example = "Jane Doe")
        String responder,

        @Schema(description = "Indicates whether the response has been marked as a solution to the topic.",
                example = "true")
        String solution) { }