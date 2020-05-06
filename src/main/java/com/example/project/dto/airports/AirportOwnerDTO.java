package com.example.project.dto.airports;

import com.example.project.entities.users.Airport;
import lombok.Data;

@Data
public class AirportOwnerDTO extends AirportBasicDTO {
    private String login;
    private String email;

    public AirportOwnerDTO(Airport owner) {
        super(owner);
        this.login = owner.getLogin();
        this.email = owner.getEmail();
    }
}
