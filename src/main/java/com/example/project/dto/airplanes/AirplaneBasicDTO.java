package com.example.project.dto.airplanes;

import com.example.project.entities.Airplane;
import com.example.project.interfaces.IInfo;
import lombok.Data;

@Data
public class AirplaneBasicDTO implements IInfo {
    private Long id;
    private String name;
    private Integer capacity;

    public AirplaneBasicDTO(Airplane airplane) {
        this.id = airplane.getId();
        this.name = airplane.getName();
        this.capacity = airplane.getCapacity();
    }
}
