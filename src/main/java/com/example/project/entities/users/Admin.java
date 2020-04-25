package com.example.project.entities.users;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class Admin extends AbstractUser {
    protected Admin() {
    }

    public Admin(String login, String password, String email) {
        super(login, password, email);
    }
}
