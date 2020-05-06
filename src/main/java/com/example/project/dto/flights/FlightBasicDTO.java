package com.example.project.dto.flights;

import com.example.project.dto.airports.AirportBasicDTO;
import com.example.project.entities.Flight;
import com.example.project.interfaces.IInfo;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FlightBasicDTO implements IInfo {
    private Long id;
    private LocalDate start;
    private LocalDate end;
    private AirportBasicDTO startAirport;
    private AirportBasicDTO endAirport;
    private Long airplane;
    private Double cost;

    public FlightBasicDTO(Flight flight) {
        this.id = flight.getId();
        this.start = flight.getStartDate();
        this.end = flight.getEndDate();
        this.startAirport = new AirportBasicDTO(flight.getStartAirport());
        this.endAirport = new AirportBasicDTO(flight.getEndAirport());
        this.airplane = flight.getAirplane().getId();
        this.cost = flight.getCost();
    }
}
