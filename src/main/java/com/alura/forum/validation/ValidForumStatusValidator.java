package com.alura.forum.validation;

import com.alura.forum.model.enums.ForumStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidForumStatusValidator implements ConstraintValidator<ValidStatus, String> {
    private String allowedValues;

    @Override
    public void initialize(ValidStatus constraintAnnotation) {
        this.allowedValues = Arrays.stream(ForumStatus.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        ForumStatus.valueOf(value);
        boolean isValid = Arrays.stream(ForumStatus.values())
                .anyMatch(status -> status.name().equals(value));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value for Forum Status. Allowed values are: " + allowedValues)
                    .addConstraintViolation();
        }

        return isValid;
    }
}


