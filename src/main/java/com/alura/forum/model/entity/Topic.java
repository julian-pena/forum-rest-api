package com.alura.forum.model.entity;

import com.alura.forum.model.entity.Course;
import com.alura.forum.model.entity.User;
import com.alura.forum.model.enums.ForumStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Topic")
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String message;

    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ForumStatus forumStatus;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses = new ArrayList<>();

}
