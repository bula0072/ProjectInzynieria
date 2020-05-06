package com.example.project.entities.users;

import com.example.project.entities.Flight;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class Airport extends AbstractUser {
    private String name;
    private Integer capacity;
    private Double latitude;
    private Double longitude;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Flight> flights;

    protected Airport() {
    }

    public Airport(String login, String password, String email, String name, Integer capacity, Double latitude, Double longitude) {
        super(login, password, email);
        this.name = name;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = true;
    }
}
