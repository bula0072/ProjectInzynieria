package com.example.project.entities;

import com.example.project.entities.users.Airline;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Data
@Entity
public class Airplane {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer capacity;

    private Double maxDistance;

    @ManyToOne
    private Airline owner;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private Set<Flight> flights;
    @OneToMany(mappedBy = "airplane")
    private Collection<Flight> flight;

    protected Airplane() {
    }

    public Airplane(String name, Integer capacity, Double maxDistance, Airline owner) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.owner = owner;
    }

    public Collection<Flight> getFlight() {
        return flight;
    }

    public void setFlight(Collection<Flight> flight) {
        this.flight = flight;
    }
}
