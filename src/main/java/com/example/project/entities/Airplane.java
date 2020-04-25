package com.example.project.entities;

import com.example.project.entities.users.AirlineOwner;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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
    private AirlineOwner owner;

    protected Airplane() {

    }
    public Airplane(String name, Integer capacity, Double maxDistance, AirlineOwner owner) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.owner = owner;
    }
}
