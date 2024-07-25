package com.alura.forum.model.dto.topic;

import com.alura.forum.model.enums.ForumStatus;

import java.time.LocalDateTime;

public record TopicInfoDTO(Long id,
                           String title,
                           String message,
                           String creationDate,
                           String forumStatus,
                           String authorName,
                           String courseName,
                           Long totalResponses) {
}
