package com.alura.forum.model.dto.topic;

import com.alura.forum.validation.UniqueTopicTitle;
import com.alura.forum.validation.ValidForumStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

@Schema(description = "DTO for updating an existing forum topic.")
public record TopicUpdateDTO(

        @Pattern(regexp = "^(?!\\s*$).+", message = "Title cannot be empty or whitespace")
        @UniqueTopicTitle
        @Schema(description = "The updated title of the topic. It must be unique and cannot be empty or whitespace.",
                example = "Updated Topic Title",
                required = true)
        String title,

        @Pattern(regexp = "^(?!\\s*$).+", message = "Message cannot be empty or whitespace")
        @Schema(description = "The updated message content of the topic. It cannot be empty or whitespace.",
                example = "This is the updated message content for the topic.",
                required = true)
        String message,

        @ValidForumStatus
        @Schema(description = "The updated status of the forum topic. It should be a valid forum status.",
                example = "CLOSED")
        String forumStatus) {
}