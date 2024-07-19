package com.alura.forum.model.dto.topic;

import com.alura.forum.model.enums.ForumStatus;

import java.time.LocalDateTime;

public record TopicInfoDTO(Long id,
                           String title,
                           String message,
                           String creationDate,
                           ForumStatus forumStatus,
                           String authorName,
                           String courseName) {
}
