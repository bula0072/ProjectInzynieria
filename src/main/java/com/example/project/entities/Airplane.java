package com.example.project.entities;

import com.example.project.entities.users.AirlineOwner;
import lombok.Data;

import javax.persistence.*;
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

    @ManyToOne(fetch = FetchType.EAGER)
    private AirlineOwner owner;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flight> flights;

    protected Airplane() {

    }

    public Airplane(String name, Integer capacity, Double maxDistance, AirlineOwner owner) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.owner = owner;
    }
}
