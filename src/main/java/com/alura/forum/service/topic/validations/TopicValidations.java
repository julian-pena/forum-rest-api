package com.alura.forum.service.topic.validations;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.model.dto.topic.TopicRegistrationDTO;

public interface TopicValidations {

    void valid(TopicRegistrationDTO registrationDTO);

}
