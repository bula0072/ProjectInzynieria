package com.example.project.dto.airports;

import com.example.project.entities.users.Airport;
import com.example.project.interfaces.IInfo;
import lombok.Data;

@Data
public class AirportBasicDTO implements IInfo {
    private Long id;
    private String name;
    private Integer capacity;
    private Double latitude, longitude;
    private Boolean active;

    public AirportBasicDTO(Airport owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.capacity = owner.getCapacity();
        this.latitude = owner.getLatitude();
        this.longitude = owner.getLongitude();
        this.active = owner.getActive();
    }
}
