package com.alura.forum.model.dto.course;

import com.alura.forum.validation.UniqueCourseName;
import com.alura.forum.validation.ValidCourseCategory;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data required to update an existing course")
public record CourseUpdateDTO(
        @Schema(description = "New name for the course", example = "Introduction to Java")
        @UniqueCourseName
        String name,

        @Schema(description = "New category for the course", example = "Software Development")
        @ValidCourseCategory
        String category
) {}
