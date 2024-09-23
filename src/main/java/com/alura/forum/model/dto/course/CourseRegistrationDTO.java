package com.alura.forum.model.dto.course;

import com.alura.forum.validation.UniqueCourseName;
import com.alura.forum.validation.ValidCourseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Schema(description = "Data required to register a new course")
public record CourseRegistrationDTO(
        @Schema(description = "Name of the course", example = "Advanced Java Programming")
        @NotEmpty(message = "Course name can not be null or blank")
        @UniqueCourseName
        String name,

        @Schema(description = "Category of the course", example = "Programming")
        @NotEmpty(message = "Course category can not be null or blank")
        @ValidCourseCategory
        String category
) {}