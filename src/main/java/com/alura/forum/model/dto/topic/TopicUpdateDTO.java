package com.alura.forum.model.dto.topic;

import com.alura.forum.validation.UniqueTopicTitle;
import com.alura.forum.validation.ValidForumStatus;
import jakarta.validation.constraints.Pattern;

public record TopicUpdateDTO(@Pattern(regexp = "^(?!\\s*$).+", message = "Title cannot be empty or whitespace")
                             @UniqueTopicTitle String title,
                             @Pattern(regexp = "^(?!\\s*$).+", message = "Message cannot be empty or whitespace")
                             String message,
                             @ValidForumStatus String forumStatus
                             ) {
}
