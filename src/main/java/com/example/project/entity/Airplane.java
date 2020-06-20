package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "airplanes")
public class Airplane {
    @Id
    @GeneratedValue
    @Column(name = "id")
    public Long id;

    @NotBlank
    @Column(name = "name")
    public String name;

    @NotNull
    @Column(name = "capacity")
    public Integer capacity;

    @NotNull
    @Column(name = "max_distance")
    public Double maxDistance;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    public User user;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Flight> flight;

    public Airplane() {
    }

    public Airplane(String name, Integer capacity, Double maxDistance, User user) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Double getMaxDistance() {
        return maxDistance;
    }

    public void setMaxDistance(Double maxDistance) {
        this.maxDistance = maxDistance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Flight> getFlight() {
        return flight;
    }

    public void setFlight(Set<Flight> flight) {
        this.flight = flight;
    }
}
