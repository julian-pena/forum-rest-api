package com.alura.forum.validation;

import com.alura.forum.repository.CourseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueCourseNameValidator implements ConstraintValidator<UniqueCourseName, String> {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void initialize(UniqueCourseName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String courseName, ConstraintValidatorContext context) {
        if (courseRepository.existsByName(courseName)) {
            // Disable default constraint violation
            context.disableDefaultConstraintViolation();
            // Build a new constraint violation with the desired message
            context.buildConstraintViolationWithTemplate("Course name already exists: " + courseName)
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
