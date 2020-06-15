package com.example.project.dto;

import com.example.project.entity.Airport;
import lombok.Data;

@Data
public class AirportDto1 {
    private Long id;
    private String name;
    private Integer capacity;
    private Double latitude, longitude;
    private Boolean active;
    private UserDto1 owner;

    public AirportDto1(Airport airport) {
        this.id = airport.getId();
        this.name = airport.getName();
        this.capacity = airport.getCapacity();
        this.latitude = airport.getLatitude();
        this.longitude = airport.getLongitude();
        this.active = airport.getActive();
        this.owner = new UserDto1(airport.getUser());
    }
}
