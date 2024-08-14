package com.alura.forum.validation;

import com.alura.forum.model.enums.ForumStatus;
import com.alura.forum.model.enums.RoleEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.stream.Collectors;

public class ValidRoleValidator implements ConstraintValidator<ValidRole, String> {

    private String allowedRoles;

    @Override
    public void initialize(ValidRole constraintAnnotation) {
        this.allowedRoles = Arrays.stream(RoleEnum.values())
                .map(Enum::name)
                .collect(Collectors.joining(", "));
        ConstraintValidator.super.initialize(constraintAnnotation);    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null)  return true;
        boolean isValid = Arrays.stream(RoleEnum.values())
                .anyMatch(role -> role.name().equals(value.toUpperCase()));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            "Invalid value for Role. Allowed roles are: " + allowedRoles)
                    .addConstraintViolation();
        }
        return isValid;
    }
}
