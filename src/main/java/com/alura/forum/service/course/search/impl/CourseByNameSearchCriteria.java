package com.alura.forum.service.course.search.impl;

import com.alura.forum.model.entity.Course;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.service.course.search.CourseSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class CourseByNameSearchCriteria implements CourseSearchCriteria {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseByNameSearchCriteria(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Page<Course> applyCriteria(Pageable pageable, String value) {
        return courseRepository.findByNameContainingIgnoreCase(value, pageable);
    }
}
