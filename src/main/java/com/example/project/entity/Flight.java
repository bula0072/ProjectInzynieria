package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Set;

@Data
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "cost")
    private Double cost;

    @NotNull
    @Column(name = "start_date")
    private Timestamp startDate;

    @NotNull
    @Column(name = "end_date")
    private Timestamp endDate;

    @ManyToOne
    @JoinColumn(name = "start_airports_id", nullable = false)
    private Airport startAirport;

    @ManyToOne
    @JoinColumn(name = "end_airports_id", nullable = false)
    private Airport endAirport;

    @ManyToOne
    @JoinColumn(name = "airplanes_id", nullable = false)
    private Airplane airplane;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE)
    private Set<Ticket> tickets;

    public Flight() {
    }

    public Flight(Double cost, Timestamp startDate, Timestamp endDate, Airport startAirport, Airport endAirport, Airplane airplane) {
        this.cost = cost;
        this.startAirport = startAirport;
        this.endAirport = endAirport;
        this.startDate = startDate;
        this.endDate = endDate;
        this.airplane = airplane;
    }
}
