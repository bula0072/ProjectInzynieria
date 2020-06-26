package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long id;

    @Column(name = "quantity")
    public Integer quantity;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    public User user;

    @ManyToOne
    @JoinColumn(name = "flights_id", nullable = false)
    public Flight flight;

    public Ticket() {
    }

    public Ticket(Integer quantity, User user, Flight flight) {
        this.quantity = quantity;
        this.user = user;
        this.flight = flight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }
}
