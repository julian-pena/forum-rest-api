package com.alura.forum.service.course.search;

import com.alura.forum.model.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseSearchCriteria {


    Page<Course> applyCriteria(Pageable pageable,String value);

}
