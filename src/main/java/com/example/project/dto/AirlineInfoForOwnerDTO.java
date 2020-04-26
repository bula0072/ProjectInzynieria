package com.example.project.dto;

import com.example.project.entities.users.AirlineOwner;
import com.example.project.interfaces.IAirlineInfo;
import lombok.Data;

import java.util.List;

@Data
public class AirlineInfoForOwnerDTO implements IAirlineInfo {
    private Long id;
    private String login;
    private String name;
    private List<AirplaneDTO> airplaneDTOList;


    public AirlineInfoForOwnerDTO(AirlineOwner airlineOwner, List<AirplaneDTO> planes) {
        this.id = airlineOwner.getId();
        this.login = airlineOwner.getLogin();
        this.name = airlineOwner.getName();
        this.airplaneDTOList = planes;
    }
}
