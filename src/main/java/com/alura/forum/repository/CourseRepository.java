package com.alura.forum.repository;

import com.alura.forum.model.entity.Course;
import com.alura.forum.model.enums.CourseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByCourseCategory(CourseCategory category, Pageable pageable);

    Page<Course> findByNameContainingIgnoreCase(String value, Pageable pageable);

    boolean existsByName(String courseName);
}
