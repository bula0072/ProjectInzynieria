package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "capacity")
    private Integer capacity;

    @NotNull
    @Column(name = "latitude")
    private Double latitude;

    @NotNull
    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "active")
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    @OneToMany(mappedBy = "startAirport", cascade = CascadeType.REMOVE)
    private Set<Flight> startFlight;

    @OneToMany(mappedBy = "endAirport", cascade = CascadeType.REMOVE)
    private Set<Flight> endFlight;

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
}
