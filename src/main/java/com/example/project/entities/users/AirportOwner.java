package com.example.project.entities.users;

import lombok.Data;

import javax.persistence.Entity;

@Data
@Entity
public class AirportOwner extends AbstractUser {
    private String ownerName;
    private Integer capacity;
    private Double latitude;
    private Double longitude;
    private Boolean active;

    protected AirportOwner() {

    }

    public AirportOwner(String login, String password, String email, String ownerName, Integer capacity, Double latitude, Double longitude) {
        super(login, password, email);
        this.ownerName = ownerName;
        this.capacity = capacity;
        this.latitude = latitude;
        this.longitude = longitude;
        this.active = true;
    }
}
