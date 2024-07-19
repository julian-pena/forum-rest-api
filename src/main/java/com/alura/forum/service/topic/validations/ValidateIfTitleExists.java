package com.alura.forum.service.topic.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.repository.TopicRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfTitleExists implements TopicValidations{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void valid(TopicRegistrationDTO registrationDTO) throws ResourceNotFoundException {
        if(topicRepository.existsByTitle(registrationDTO.title())){
            throw new ValidationException("Title already exists. Use a different title.");
        }
    }
}
