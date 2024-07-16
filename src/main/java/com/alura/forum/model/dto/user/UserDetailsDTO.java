package com.alura.forum.model.dto.user;

import java.time.LocalDate;

public record UserDetailsDTO(Long id,
                             String name,
                             String email,
                             LocalDate registrationDate) {
}
