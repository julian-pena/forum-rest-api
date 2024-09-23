package com.alura.forum.model.dto.response;

import com.alura.forum.validation.ValidLongValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO for registering a new response to a topic.")
public record ResponseRegistrationDTO(

        @Schema(description = "The content of the response message.",
                example = "This is a response to the topic.")
        @NotEmpty
        @Size(max = 1000)
        String message,

        @Schema(description = "The unique identifier of the topic to which this response belongs.",
                example = "456")
        @NotNull
        @ValidLongValue
        String topicId,

        @Schema(description = "The unique identifier of the user who is submitting the response.",
                example = "789")
        @NotNull
        @ValidLongValue
        String responderId) {
}
