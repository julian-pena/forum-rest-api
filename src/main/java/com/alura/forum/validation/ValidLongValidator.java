package com.alura.forum.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;


public class ValidLongValidator implements ConstraintValidator<ValidLongValue, String> {


    @Override
    public void initialize(ValidLongValue constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation with the desired message
            context.buildConstraintViolationWithTemplate("Inputted value is not a valid Long: " + value)
                    .addConstraintViolation();
            return false;
        }
    }
}
