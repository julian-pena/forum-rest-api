package com.alura.forum.model.dto.course;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Detailed information about a course")
public record CourseInfoDTO(
        @Schema(description = "Unique identifier of the course", example = "1")
        Long id,

        @Schema(description = "Name of the course", example = "Introduction to Java")
        String name,

        @Schema(description = "Category of the course", example = "Programming")
        String category,

        @Schema(description = "List of users enrolled in the course", example = "[\"Alice Johnson\", \"Bob Smith\"]")
        List<String> usersEnrolled
) { }
