package com.alura.forum.service.topic;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.mapper.TopicMapper;
import com.alura.forum.model.dto.topic.TopicInfoDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.model.dto.topic.TopicUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.repository.UserRepository;
import com.alura.forum.service.topic.search.TopicSearchCriteria;
import com.alura.forum.service.topic.search.TopicSearchCriteriaFactory;
import com.alura.forum.service.topic.validations.TopicValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class TopicService {

    private final TopicRepository topicRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final TopicMapper topicMapper;
    private final TopicSearchCriteriaFactory criteriaFactory;
    private final List<TopicValidations> validations;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository, CourseRepository courseRepository,
                        TopicMapper topicMapper, TopicSearchCriteriaFactory criteriaFactory, List<TopicValidations> validations){

        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.topicMapper = topicMapper;
        this.criteriaFactory = criteriaFactory;
        this.validations = validations;
    }

    @Transactional(readOnly = true)
    public Page<TopicInfoDTO> getAllTopics(Pageable pageable, String criteria, String value) {
        Page<Topic> topicPage;
        // Search for criteria and value if provided
        if (criteria != null && value != null) {
            TopicSearchCriteria searchCriteria = criteriaFactory.getCriteria(criteria);
            topicPage = searchCriteria != null ? searchCriteria.applyCriteria(pageable, value) : topicRepository.findAll(pageable);
        } else {
            topicPage = topicRepository.findAll(pageable);
        }
        // Map to DTO and return page
        List<TopicInfoDTO> topicInfoDTOS = topicMapper.topicsToTopicInfoDTOList(topicPage.getContent());
        return new PageImpl<>(topicInfoDTOS, pageable, topicPage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public TopicInfoDTO getSingleTopic(Long id) {
        Topic topic = findTopicById(id);
        return topicMapper.topicToTopicInfoDTO(topic);
    }

    @Transactional
    public TopicInfoDTO registerNewTopic(TopicRegistrationDTO registrationDTO){
        validations.forEach(v -> v.valid(registrationDTO));

        UserEntity author = userRepository.getReferenceById(Long.parseLong(registrationDTO.authorId()));
        Course course = courseRepository.getReferenceById(Long.parseLong(registrationDTO.courseId()));

        Topic topic = topicMapper.registerTopicFromDTO(registrationDTO, author, course);

        Topic savedTopic = topicRepository.save(topic);

        return topicMapper.topicToTopicInfoDTO(savedTopic);
    }

    @Transactional
    public TopicInfoDTO updateTopic(Long id, TopicUpdateDTO topicUpdateDTO) {
        // Find topic by ID. An exception will throw if ID is not found.
        Topic topicUpdated = findTopicById(id);
        // Map new properties to entity from DTO
        topicUpdated = topicMapper.updateTopicFromDTO(topicUpdateDTO, topicUpdated);
        topicRepository.save(topicUpdated);
        // Return DTO with updated properties
        return topicMapper.topicToTopicInfoDTO(topicUpdated);
    }

    @Transactional
    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        topicRepository.deleteById(id);
    }

    private Topic findTopicById(Long id) {
        return topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic not found with id: " + id));
    }
}
