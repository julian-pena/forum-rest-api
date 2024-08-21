package com.alura.forum.model.dto.topic;

import com.alura.forum.validation.UniqueTopicTitle;
import com.alura.forum.validation.ValidLongValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Schema(description = "DTO for registering a new forum topic.")
public record TopicRegistrationDTO(

        @NotEmpty
        @UniqueTopicTitle
        @Schema(description = "The title of the new topic. It must be unique.",
                example = "How to Learn Spring Boot Effectively",
                required = true)
        String title,

        @NotEmpty
        @Schema(description = "The main content or message of the new topic.",
                example = "I am looking for the best practices and resources to learn Spring Boot.",
                required = true)
        String message,

        @NotNull
        @ValidLongValue
        @Schema(description = "The unique identifier of the author creating the topic.",
                example = "101",
                required = true)
        String authorId,

        @NotNull
        @ValidLongValue
        @Schema(description = "The unique identifier of the course related to the topic.",
                example = "202",
                required = true)
        String courseId) {
}