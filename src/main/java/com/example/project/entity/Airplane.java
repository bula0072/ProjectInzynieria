package com.example.project.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Entity
@Table(name = "airplanes")
public class Airplane {
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
    @Column(name = "max_distance")
    private Double maxDistance;

    @ManyToOne
    @JoinColumn(name = "airplane_id")
    private User user;

    @OneToMany(mappedBy = "airplane", cascade = CascadeType.REMOVE)
    private Set<Flight> flight;

    public Airplane() {
    }

    public Airplane(String name, Integer capacity, Double maxDistance, User user) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
        this.user = user;
    }
}
