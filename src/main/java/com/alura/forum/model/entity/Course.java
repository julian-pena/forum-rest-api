package com.alura.forum.model.entity;

import com.alura.forum.model.enums.CourseCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.HashSet;
import java.util.Set;

@Schema(description = "Entity representing a course in the forum.")
@Entity(name = "Course")
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class Course {

    @Schema(description = "Unique identifier for the course.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Name of the course.",
            example = "Java for Beginners")
    @Column(name = "course_name")
    private String name;

    @Schema(description = "Category of the course, represented as an enum value.",
            example = "PROGRAMMING")
    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private CourseCategory courseCategory;

    @Schema(description = "Set of users enrolled in this course.")
    @ManyToMany(mappedBy = "courses")
    private Set<UserEntity> usersEnrolled = new HashSet<>();

}
