package com.example.project.entities.users;

import lombok.Data;

import javax.persistence.Entity;
import java.time.LocalDate;

@Data
@Entity
public class User extends AbstractUser {
    private String firstName;
    private String surname;
    private LocalDate dateOfBirth;

    public User(String login, String password, String email, String firstName, String surname, LocalDate dateOfBirth) {
        super(login, password, email);
        this.firstName = firstName;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
    }
}
