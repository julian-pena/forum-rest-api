package com.alura.forum.model.dto.topic;

import com.alura.forum.validation.UniqueTopicTitle;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record TopicRegistrationDTO(@NotEmpty @UniqueTopicTitle String title,
                                   @NotEmpty String message,
                                   @NotNull Long authorId,
                                   @NotNull Long courseId) {
}
