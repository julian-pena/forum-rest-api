package com.alura.forum.model.dto.response;

public record ResponseInfoDTO(Long id,
                              String message,
                              String topic,
                              String creationDate,
                              String responder,
                              String solution) {
}
