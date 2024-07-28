package com.alura.forum.service.topic.search;

import com.alura.forum.model.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TopicSearchCriteria {
    Page<Topic> applyCriteria(Pageable pageable, String value);
}
