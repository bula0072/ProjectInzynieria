package com.example.project.dto.airlines;

import com.example.project.entities.users.Airline;
import com.example.project.interfaces.IInfo;
import lombok.Data;

@Data
public class AirlineWithoutAirplaneDTO implements IInfo {
    private Long id;
    private String name;

    public AirlineWithoutAirplaneDTO(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
    }
}
