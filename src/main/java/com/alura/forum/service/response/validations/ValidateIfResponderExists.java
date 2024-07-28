package com.alura.forum.service.response.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.response.ResponseRegistrationDTO;
import com.alura.forum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidateIfResponderExists implements ResponseValidations {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void valid(ResponseRegistrationDTO registrationDTO) {
        if (!userRepository.existsById(Long.parseLong(registrationDTO.responderId()))){
            throw new ResourceNotFoundException("User not found with id: " + registrationDTO.responderId());
        }
    }
}
