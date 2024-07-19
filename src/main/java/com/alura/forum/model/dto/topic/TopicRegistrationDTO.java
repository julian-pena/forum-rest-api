package com.alura.forum.model.dto.topic;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TopicRegistrationDTO(@NotEmpty String title,
                                   @NotEmpty String message,
                                   @NotNull Long authorId,
                                   @NotNull Long courseId) {
}
