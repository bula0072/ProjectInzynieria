package com.example.project.dto;

import com.example.project.entity.User;
import lombok.Data;

@Data
public class UserDto1 {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String roles;

    public UserDto1(User user) {
        this.id = user.getId();
        this.login = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }
}
