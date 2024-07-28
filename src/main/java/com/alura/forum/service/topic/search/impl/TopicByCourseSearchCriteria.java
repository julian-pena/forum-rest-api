package com.alura.forum.service.topic.search.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.service.topic.search.TopicSearchCriteria;

@Component
public class TopicByCourseSearchCriteria implements TopicSearchCriteria {

    private final TopicRepository topicRepository;

    @Autowired
    public TopicByCourseSearchCriteria(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @Override
    public Page<Topic> applyCriteria(Pageable pageable, String value) {
        return topicRepository.findByCourseNameContainingIgnoreCase(value, pageable);
    }
}
