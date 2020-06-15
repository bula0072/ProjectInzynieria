package com.example.project.dto;

import com.example.project.entity.Airport;
import lombok.Data;

@Data
public class AirportDto2 {
    private Long id;
    private String name;

    public AirportDto2(Airport airport) {
        this.id = airport.getId();
        this.name = airport.getName();
    }
}
