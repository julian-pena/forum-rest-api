package com.alura.forum.model.dto.topic;

import com.alura.forum.validation.ValidStatus;

public record TopicUpdateDTO(String title,
                             String message,
                             @ValidStatus String status
                             ) {
}
