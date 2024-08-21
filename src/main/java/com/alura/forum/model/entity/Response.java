package com.alura.forum.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Schema(description = "Entity representing a response to a topic in the forum.")
@Entity(name = "Response")
@Table(name = "responses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    @Schema(description = "Unique identifier for the response.",
            example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Content of the response message.",
            example = "This is a detailed response to the topic.")
    @Column(name = "message", nullable = false)
    private String message;

    @Schema(description = "The topic to which this response belongs.",
            implementation = Topic.class)
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Schema(description = "The date and time when the response was created.",
            example = "2024-08-20T12:45:30")
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Schema(description = "The user who posted the response.",
            implementation = UserEntity.class)
    @ManyToOne
    @JoinColumn(name = "responder_id", nullable = false)
    private UserEntity responder;

    @Schema(description = "Indicates whether this response is marked as the solution.",
            example = "true")
    private Boolean solution;
}