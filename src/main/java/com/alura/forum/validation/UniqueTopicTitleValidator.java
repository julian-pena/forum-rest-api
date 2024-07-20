package com.alura.forum.validation;

import com.alura.forum.repository.TopicRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueTopicTitleValidator implements ConstraintValidator<UniqueTopicTitle, String> {

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public void initialize(UniqueTopicTitle constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String title, ConstraintValidatorContext context) {
        if(topicRepository.existsByTitle(title)) { // Disable default constraint violation
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation with the desired message
            context.buildConstraintViolationWithTemplate("Title  already exists: " + title + ". Use a different title")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

