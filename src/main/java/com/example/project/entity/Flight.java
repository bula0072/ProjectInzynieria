package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Set;

@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long id;

    @NotNull
    @Column(name = "cost")
    public Double cost;

    @NotNull
    @Column(name = "start_date")
    public Instant startDate;

    @NotNull
    @Column(name = "end_date")
    public Instant endDate;

    @ManyToOne
    @JoinColumn(name = "start_airports_id", nullable = false)
    public Airport startAirport;

    @ManyToOne
    @JoinColumn(name = "end_airports_id", nullable = false)
    public Airport endAirport;

    @ManyToOne
    @JoinColumn(name = "airplanes_id", nullable = false)
    public Airplane airplane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Ticket> tickets;

    public Flight() {
    }

    public Flight(Double cost, Instant startDate, Instant endDate, Airport startAirport, Airport endAirport, Airplane airplane) {
        this.cost = cost;
        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.startDate = startDate;
        this.endDate = endDate;
        this.airplane = airplane;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Airport getStartAirport() {
        return startAirport;
    }

    public void setStartAirport(Airport startAirport) {
        this.startAirport = startAirport;
    }

    public Airport getEndAirport() {
        return endAirport;
    }

    public void setEndAirport(Airport endAirport) {
        this.endAirport = endAirport;
    }

    public Airplane getAirplane() {
        return airplane;
    }

    public void setAirplane(Airplane airplane) {
        this.airplane = airplane;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
