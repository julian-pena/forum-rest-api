package com.alura.forum.service.topic.search.impl;

import com.alura.forum.model.entity.Topic;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.service.topic.search.TopicSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class TopicByAuthorSearchCriteria implements TopicSearchCriteria {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicByAuthorSearchCriteria(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Page<Topic> applyCriteria(Pageable pageable, String value) {
        return topicRepository.findByAuthorNameContainingIgnoreCase(value, pageable);
    }
}
