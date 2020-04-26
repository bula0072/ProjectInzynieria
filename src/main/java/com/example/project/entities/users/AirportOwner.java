package com.example.project.entities.users;

import com.example.project.entities.Flight;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Data
@Entity
public class AirportOwner extends AbstractUser {
    private String name;
    private Integer capacity;
    private Double latitude;
    private Double longitude;
    private Boolean active;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flight> flights;

    protected AirportOwner() {

    }

    public AirportOwner(String login, String password, String email, String name, Integer capacity, Double latitude, Double longitude) {
        super(login, password, email);
        this.name = name;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = true;
    }
}
