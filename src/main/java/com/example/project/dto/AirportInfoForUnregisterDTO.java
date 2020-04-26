package com.example.project.dto;

import com.example.project.entities.users.AirportOwner;
import com.example.project.interfaces.IAirportInfo;
import lombok.Data;

@Data
public class AirportInfoForUnregisterDTO implements IAirportInfo {
    private Long id;
    private String name;
    private Integer capacity;
    private Double latitude, longitude;
    private Boolean active;

    public AirportInfoForUnregisterDTO(AirportOwner owner) {
        this.id = owner.getId();
        this.name = owner.getName();
        this.capacity = owner.getCapacity();
        this.latitude = owner.getLatitude();
        this.longitude = owner.getLongitude();
        this.active = owner.getActive();
    }
}
