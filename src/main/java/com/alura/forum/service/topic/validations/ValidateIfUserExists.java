package com.alura.forum.service.topic.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfUserExists implements TopicValidations{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void valid(TopicRegistrationDTO registrationDTO) throws ResourceNotFoundException {
        if (!userRepository.existsById(registrationDTO.authorId())) {
            throw new ResourceNotFoundException("User not found with id: " + registrationDTO.authorId());
        }
    }
}
