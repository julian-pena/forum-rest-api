package com.alura.forum.model.dto.response;

import com.alura.forum.validation.ValidLongValue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ResponseRegistrationDTO(@NotEmpty String message,
                                      @NotNull @ValidLongValue String topicId,
                                      @NotNull @ValidLongValue String responderId) {
}
