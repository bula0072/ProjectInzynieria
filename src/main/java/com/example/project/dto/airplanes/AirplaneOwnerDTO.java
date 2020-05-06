package com.example.project.dto.airplanes;

import com.example.project.dto.airlines.AirlineWithoutAirplaneDTO;
import com.example.project.entities.Airplane;
import lombok.Data;

@Data
public class AirplaneOwnerDTO extends AirplaneBasicDTO {
    private Double maxDistance;
    private AirlineWithoutAirplaneDTO airline;

    public AirplaneOwnerDTO(Airplane airplane) {
        super(airplane);
        this.maxDistance = airplane.getMaxDistance();
        this.airline = new AirlineWithoutAirplaneDTO(airplane.getOwner());
    }
}
