package com.example.project.dto;

import com.example.project.entity.Flight;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class FlightDto1 {
    private Long id;
    private Double cost;
    private Timestamp startDate;
    private Timestamp endDate;
    private AirportDto2 startAirport;
    private AirportDto2 endAirport;
    private AirplaneDto1 airplane;

    public FlightDto1(Flight flight) {
        this.id = flight.getId();
        this.cost = flight.getCost();
        this.startDate = flight.getStartDate();
        this.endDate = flight.getEndDate();
        this.startAirport = new AirportDto2(flight.getStartAirport());
        this.endAirport = new AirportDto2(flight.getEndAirport());
    }
}
