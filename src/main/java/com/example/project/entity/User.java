package com.example.project.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "login")
    private String login;

    @NotBlank
    @Column(name = "password")
    private String password;

    @NotBlank
    @Column(name = "email")
    private String email;

    @Column(name = "roles")
    private String roles;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Airline airline;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Airport airport;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<Airplane> airplane;

    public User(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = "USER";
    }

    public User(String login, String password, String email, String roles) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public List<String> getRoleList() {
        if (this.roles.length() > 0)
            return Arrays.asList(this.roles.split(","));
        return new ArrayList<>();
    }
}
