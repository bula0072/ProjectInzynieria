package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@Table(name = "airlines")
public class Airline {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    @NotBlank
    private String name;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    public Airline(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public Airline() {
    }
}
