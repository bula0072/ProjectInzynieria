package com.example.project.dto.airlines;

import com.example.project.dto.airplanes.AirplaneDTO;
import com.example.project.entities.users.Airline;
import com.example.project.interfaces.IInfo;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class AirlineBasicDTO implements IInfo {
    private Long id;
    private String name;
    private List<IInfo> airplanes;

    public AirlineBasicDTO(Airline airline) {
        this.id = airline.getId();
        this.name = airline.getName();
        airplanes = airline.getAirplanes().stream().map(new AirplaneDTO()::getBasic).collect(Collectors.toList());
    }
}
