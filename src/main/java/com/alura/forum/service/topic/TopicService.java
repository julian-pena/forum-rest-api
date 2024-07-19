package com.alura.forum.service.topic;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.mapper.TopicMapper;
import com.alura.forum.model.dto.topic.TopicInfoDTO;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.model.dto.topic.TopicUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.Topic;
import com.alura.forum.model.entity.User;
import com.alura.forum.model.enums.ForumStatus;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.repository.TopicRepository;
import com.alura.forum.repository.UserRepository;
import com.alura.forum.service.topic.validations.TopicValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService {

    private final TopicRepository topicRepository;

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final TopicMapper topicMapper;

    private final List<TopicValidations> validations;

    @Autowired
    public TopicService(TopicRepository topicRepository, UserRepository userRepository, CourseRepository courseRepository,
                        List<TopicValidations> validations, TopicMapper topicMapper){

        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.validations = validations;
        this.topicMapper = topicMapper;
    }


    public Page<TopicInfoDTO> getAllTopics(Pageable pageable, String criteria, String value) {
        // Sort page by creationDate for any request
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Direction.ASC, "creationDate"));
        Page<Topic> topicPage;
        // Search for criteria and value if provided
        if (criteria != null && value != null) {
            topicPage = switch (criteria.toLowerCase()) {
                case "title" -> topicRepository.findByTitleContainingIgnoreCase(value, sortedPageable);
                case "author" -> topicRepository.findByAuthorNameContainingIgnoreCase(value, sortedPageable);
                case "course" -> topicRepository.findByCourseNameContainingIgnoreCase(value, sortedPageable);
                default -> topicRepository.findAll(sortedPageable);
            };
        // If no criteria nor value was provided, then return page of tables  ONLY ordered by ASC order
        } else topicPage = topicRepository.findAll(sortedPageable);
        // Map to DTO and return page
        List<TopicInfoDTO> topicInfoDTOS = topicMapper.topicsToTopicInfoDTOList(topicPage.getContent());
        return new PageImpl<>(topicInfoDTOS, pageable, topicPage.getTotalElements());
    }

    public TopicInfoDTO getSingleTopic(Long id) {
        Topic topic = findTopicById(id);
        return topicMapper.topicToTopicInfoDTO(topic);
    }

    public TopicInfoDTO registerNewTopic(TopicRegistrationDTO registrationDTO){
        validations.forEach(v -> v.valid(registrationDTO));

        User user = userRepository.getReferenceById(registrationDTO.authorId());
        Course course = courseRepository.getReferenceById(registrationDTO.courseId());

        Topic topic = topicMapper.registerTopicFromDTO(registrationDTO, user, course);

        topic.setCreationDate(LocalDateTime.now());
        topic.setForumStatus(ForumStatus.OPEN);

        Topic savedTopic = topicRepository.save(topic);

        return topicMapper.topicToTopicInfoDTO(savedTopic);
    }



    private Topic findTopicById(Long id) throws ResourceNotFoundException {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        return optionalTopic.get();
    }

    public TopicInfoDTO updateTopic(Long id, TopicUpdateDTO topicUpdateDTO) {
        // Find topic by ID. An exception will throw if ID is not found.
        Topic topicUpdated = findTopicById(id);
        // Map new properties to entity from DTO
        topicUpdated = topicMapper.updateTopicFromDTO(topicUpdateDTO, topicUpdated);
        topicRepository.save(topicUpdated);
        // Return DTO with updated properties
        return topicMapper.topicToTopicInfoDTO(topicUpdated);
    }

    public void deleteTopic(Long id) {
        if (!topicRepository.existsById(id)) {
            throw new ResourceNotFoundException("Topic not found with id: " + id);
        }
        topicRepository.deleteById(id);
    }
}
