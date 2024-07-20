package com.alura.forum.validation;

import com.alura.forum.model.enums.ForumStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidForumStatusValidator implements ConstraintValidator<ValidForumStatus, String> {
    private String allowedValues;

    @Override
    public void initialize(ValidForumStatus constraintAnnotation) {
        this.allowedValues = Arrays.stream(ForumStatus.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)  return true;

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


