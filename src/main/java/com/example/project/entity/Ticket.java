package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "flights_id", nullable = false)
    private Flight flight;
}
