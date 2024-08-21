package com.alura.forum.model.dto.topic;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO representing information about a forum topic.")
public record TopicInfoDTO(

        @Schema(description = "The unique identifier of the topic.",
                example = "1")
        Long id,

        @Schema(description = "The title of the topic.",
                example = "How to Learn Spring Boot Effectively")
        String title,

        @Schema(description = "The message or content of the topic.",
                example = "I would like to know the best resources and practices for learning Spring Boot.")
        String message,

        @Schema(description = "The date and time when the topic was created.",
                example = "2024-08-16T09:30:00Z")
        String creationDate,

        @Schema(description = "The current status of the forum topic, e.g., OPEN, CLOSED.",
                example = "OPEN")
        String forumStatus,

        @Schema(description = "The name of the author who created the topic.",
                example = "Alice Johnson")
        String authorName,

        @Schema(description = "The name of the course related to the topic.",
                example = "Java for Beginners")
        String courseName,

        @Schema(description = "The total number of responses to the topic.",
                example = "5")
        Long totalResponses) {
}