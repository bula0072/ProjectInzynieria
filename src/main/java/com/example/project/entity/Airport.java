package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "airports")
public class Airport {
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
    @Column(name = "latitude")
    public Double latitude;

    @NotNull
    @Column(name = "longitude")
    public Double longitude;

    @Column(name = "active")
    public Boolean active;

    @OneToOne(cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    public User user;

    @OneToMany(mappedBy = "startAirport", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Flight> startFlight;

    @OneToMany(mappedBy = "endAirport", cascade = CascadeType.REMOVE, orphanRemoval = true)
    public Set<Flight> endFlight;

    public Airport() {
    }

    public Airport(String name, Integer capacity, Double latitude, Double longitude, Boolean active, User owner) {
        this.name = name;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = active;
        this.user = owner;
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Flight> getStartFlight() {
        return startFlight;
    }

    public void setStartFlight(Set<Flight> startFlight) {
        this.startFlight = startFlight;
    }

    public Set<Flight> getEndFlight() {
        return endFlight;
    }

    public void setEndFlight(Set<Flight> endFlight) {
        this.endFlight = endFlight;
    }
}
