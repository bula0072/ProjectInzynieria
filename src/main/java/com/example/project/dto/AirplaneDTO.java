package com.example.project.dto;

import com.example.project.entities.Airplane;
import lombok.Data;

@Data
public class AirplaneDTO {
    private String name;
    private Integer capacity;
    private Double maxDistance;

    public AirplaneDTO(String name, Integer capacity, Double maxDistance) {
        this.name = name;
        this.capacity = capacity;
        this.maxDistance = maxDistance;
    }

    public AirplaneDTO(Airplane plane) {
        this.name = plane.getName();
        this.capacity = plane.getCapacity();
        this.maxDistance = plane.getMaxDistance();
    }
}
