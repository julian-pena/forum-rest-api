package com.alura.forum.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity(name = "Response")
@Table(name = "responses")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "message", nullable = false)
    private String message;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @ManyToOne
    @JoinColumn(name = "responder_id", nullable = false)
    private User responder;

    private Boolean solution;

}
