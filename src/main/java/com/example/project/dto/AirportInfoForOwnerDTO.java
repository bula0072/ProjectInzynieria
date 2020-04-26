package com.example.project.dto;

import com.example.project.entities.users.AirportOwner;
import com.example.project.interfaces.IAirportInfo;
import lombok.Data;

@Data
public class AirportInfoForOwnerDTO implements IAirportInfo {
    private Long id;
    private String login;
    private String email;
    private String name;
    private Integer capacity;
    private Double latitude, longitude;
    private Boolean active;

    public AirportInfoForOwnerDTO(AirportOwner owner) {
        this.id = owner.getId();
        this.login = owner.getLogin();
        this.email = owner.getEmail();
        this.name = owner.getName();
        this.capacity = owner.getCapacity();
        this.latitude = owner.getLatitude();
        this.longitude = owner.getLongitude();
        this.active = owner.getActive();
    }
}
