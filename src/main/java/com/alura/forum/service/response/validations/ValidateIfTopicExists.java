package com.alura.forum.service.response.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class ValidateIfTopicExists implements ResponseValidations{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void valid(ResponseRegistrationDTO registrationDTO) {
        if(!topicRepository.existsById(Long.parseLong(registrationDTO.topicId()))){
            throw new ResourceNotFoundException("Topic not found with ID: " + registrationDTO.responderId());
        }

    }
}
