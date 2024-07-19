package com.alura.forum.service.topic.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfCourseExists implements  TopicValidations {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void valid(TopicRegistrationDTO registrationDTO) throws ResourceNotFoundException {
        if(!courseRepository.existsById(registrationDTO.courseId())) {
            throw new ResourceNotFoundException("Course id was not found " + registrationDTO.courseId());
        }
    }
}

