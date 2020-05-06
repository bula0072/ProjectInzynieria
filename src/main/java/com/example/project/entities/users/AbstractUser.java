package com.example.project.entities.users;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public abstract class AbstractUser {
    @Id
    @GeneratedValue
    private Long id;

    private String login;

    private String password;

    private String email;

    public AbstractUser() {
    }

    public AbstractUser(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }
}
