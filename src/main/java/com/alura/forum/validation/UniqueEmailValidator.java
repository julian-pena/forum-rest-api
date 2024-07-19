package com.alura.forum.validation;

import com.alura.forum.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private UserRepository userRepository;


    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (userRepository.existsByEmail(email)) {
            // Disable default constraint violation
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation with the desired message
            context.buildConstraintViolationWithTemplate("Email already exists: " + email)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
