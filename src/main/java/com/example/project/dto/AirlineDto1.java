package com.example.project.dto;

import com.example.project.entity.Airline;
import lombok.Data;

@Data
public class AirlineDto1 {
    private Long id;
    private String name;
    private UserDto1 user;

    public AirlineDto1(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
        this.user = new UserDto1(airline.getUser());
    }
}
