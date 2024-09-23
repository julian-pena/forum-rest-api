package com.alura.forum;

import com.alura.forum.mapper.CourseMapper;
import com.alura.forum.model.dto.course.CourseInfoDTO;
import com.alura.forum.model.entity.Course;
import com.alura.forum.model.enums.CourseCategory;
import com.alura.forum.repository.CourseRepository;
import com.alura.forum.service.course.search.CourseSearchCriteria;
import com.alura.forum.service.course.search.CourseSearchCriteriaFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@SpringBootApplication
public class ForumApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForumApplication.class, args);
	}
}


