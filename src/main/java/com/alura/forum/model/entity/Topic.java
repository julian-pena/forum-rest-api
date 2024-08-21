package com.alura.forum.model.entity;

import com.alura.forum.model.enums.ForumStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Entity representing a forum topic.")
@Entity(name = "Topic")
@Table(name = "topics")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Topic {

    @Schema(description = "Unique identifier for the topic.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Title of the topic, which must be unique.",
            example = "Understanding Spring Boot")
    @Column(nullable = false, unique = true)
    private String title;

    @Schema(description = "Message or content of the topic.",
            example = "This topic covers the basics of Spring Boot...")
    @Column(nullable = false)
    private String message;

    @Schema(description = "Date and time when the topic was created.",
            example = "2024-08-16T09:30:00")
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;

    @Schema(description = "Current status of the topic, such as OPEN or CLOSED.",
            example = "OPEN")
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ForumStatus forumStatus;

    @Schema(description = "The author of the topic.",
            implementation = UserEntity.class)
    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Schema(description = "The course associated with this topic.",
            implementation = Course.class)
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Schema(description = "List of responses to this topic.",
            implementation = Response.class)
    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Response> responses = new ArrayList<>();
}
