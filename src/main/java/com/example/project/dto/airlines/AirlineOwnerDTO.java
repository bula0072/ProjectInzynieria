package com.example.project.dto.airlines;

import com.example.project.entities.users.Airline;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AirlineOwnerDTO extends AirlineBasicDTO {
    private Long id;
    private String login;
    private String email;

    public AirlineOwnerDTO(Airline owner) {
        super(owner);
        this.id = owner.getId();
        this.login = owner.getLogin();
        this.email = owner.getEmail();
    }
}
