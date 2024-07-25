package com.alura.forum.repository;

import com.alura.forum.model.entity.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ResponseRepository extends JpaRepository<Response, Long> {
    Page<Response> findByTopicTitleContainingIgnoreCase(String topicTitle, Pageable pageable);

    Page<Response> findByMessageContainingIgnoreCase(String message, Pageable pageable);

    Page<Response> findByResponderNameContainingIgnoreCase(String responderName, Pageable pageable);

    @Query("""
            SELECT r FROM Response r WHERE r.solution = :solution
            """)
    Page<Response> findBySolution(Boolean solution, Pageable pageable);
}
