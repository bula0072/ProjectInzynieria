package com.example.project.dto;

import com.example.project.entity.Airplane;
import lombok.Data;

@Data
public class AirplaneDto1 {
    private Long id;
    private String name;
    private Integer capacity;
    private Double maxDistance;

    public AirplaneDto1(Airplane airplane) {
        this.id = airplane.getId();
        this.name = airplane.getName();
        this.capacity = airplane.getCapacity();
        this.maxDistance = airplane.getMaxDistance();
    }
}
