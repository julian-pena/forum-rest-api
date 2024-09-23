package com.alura.forum.service.course;

import com.alura.forum.exception.ResourceNotFoundException;
import com.alura.forum.mapper.CourseMapper;
import com.alura.forum.model.dto.course.CourseInfoDTO;
import com.alura.forum.model.dto.course.CourseRegistrationDTO;
import com.alura.forum.model.dto.course.CourseUpdateDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.UserEntity;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.repository.UserRepository;
import com.alura.forum.service.course.search.CourseSearchCriteria;
import com.alura.forum.service.course.search.CourseSearchCriteriaFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final CourseMapper courseMapper;

    private final CourseSearchCriteriaFactory criteriaFactory;

    @Autowired
    public CourseService(CourseRepository courseRepository, UserRepository userRepository,
                         CourseMapper courseMapper, CourseSearchCriteriaFactory criteriaFactory){
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.courseMapper = courseMapper;
        this.criteriaFactory = criteriaFactory;
    }


    @Transactional
    public CourseInfoDTO getSingleCourse(Long id) {
        Course course = findCourseById(id);
        return courseMapper.courseToCourseDTO(course);
    }

    @Transactional
    public CourseInfoDTO addNewCourse(CourseRegistrationDTO registrationDTO) {
        Course course = courseMapper.registerNewCourse(registrationDTO);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.courseToCourseDTO(savedCourse);
    }

    @Transactional(readOnly = true)
    public Page<CourseInfoDTO> getCourses(Pageable pageable, String criteria, String value) {
        Page<Course> coursePage;
        if(criteria != null && value != null){
            CourseSearchCriteria searchCriteria = criteriaFactory.getCriteria(criteria);
            coursePage = searchCriteria != null ? searchCriteria.applyCriteria(pageable, value) : courseRepository.findAll(pageable);
        } else {
            coursePage = courseRepository.findAll(pageable);
        }
        List<CourseInfoDTO> courseInfoDTOS = courseMapper.coursesToCourseDTO(coursePage.getContent());
        return new PageImpl<>(courseInfoDTOS, pageable, coursePage.getTotalElements());
    }

    @Transactional
    public CourseInfoDTO updateCourseFromDTO(Long id, CourseUpdateDTO courseUpdateDTO) {
        Course courseUpdated = findCourseById(id);
        courseUpdated = courseMapper.updateCourseFromDTO(courseUpdateDTO, courseUpdated);
        courseRepository.save(courseUpdated);
        return courseMapper.courseToCourseDTO(courseUpdated);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = findCourseById(id);

        // Get the users enrolled in the course
        List<UserEntity> usersEnrolled = course.getUsersEnrolled();

        // Remove the course from each user's enrolled courses list
        for (UserEntity user : usersEnrolled) {
            user.getCourses().remove(course);
            userRepository.save(user); // Persist the changes for each user
        }

        courseRepository.delete(course);
    }



    private Course findCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id: " + id));
    }


}
