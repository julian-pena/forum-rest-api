package com.alura.forum.repository;

import com.alura.forum.model.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    Page<Topic> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    Page<Topic> findByAuthorNameContainingIgnoreCase(String authorName, Pageable pageable);

    Page<Topic> findByCourseNameContainingIgnoreCase(String  courseName, Pageable pageable);

    boolean existsByTitle(String title);
}
