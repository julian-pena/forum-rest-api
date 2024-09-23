package com.alura.forum.validation;

import com.alura.forum.model.enums.CourseCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidCourseCategoryValidator implements ConstraintValidator<ValidCourseCategory, String> {

    private String allowedValues;

    @Override
    public void initialize(ValidCourseCategory constraintAnnotation) {
        this.allowedValues = Arrays.stream(CourseCategory.values())
                .map(CourseCategory::name)
                .collect(Collectors.joining(", "));

        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;
        boolean isValid = Arrays.stream(CourseCategory.values())
                .anyMatch(courseCategory -> courseCategory.name().equals(value.toUpperCase()));

        if(!isValid){
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Invalid value for Course Category. Allowed values are: " + allowedValues)
                    .addConstraintViolation();
        }
        return isValid;
    }

}
