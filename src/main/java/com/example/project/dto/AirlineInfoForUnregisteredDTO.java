package com.example.project.dto;

import com.example.project.entities.Airplane;
import com.example.project.entities.users.AirlineOwner;
import com.example.project.interfaces.IAirlineInfo;
import lombok.Data;

import java.util.List;

@Data
public class AirlineInfoForUnregisteredDTO implements IAirlineInfo {
    private String name;
    private List<AirplaneDTO> airplanes;

    public AirlineInfoForUnregisteredDTO(String name, List<AirplaneDTO> airplanes) {
        this.name = name;
        this.airplanes = airplanes;
    }

    public AirlineInfoForUnregisteredDTO(AirlineOwner airlineOwner, List<AirplaneDTO> newPlanes) {
        this.name = airlineOwner.getName();
        this.airplanes = newPlanes;
    }
}
