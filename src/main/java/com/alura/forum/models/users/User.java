package com.alura.forum.models.users;

import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity(name = "User")
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String name;

    private String email;

    private String password;

    @Column(name = "creation_date")
    private Date creationDate;

}
