package com.example.project.dto.airports;

import com.example.project.entities.users.Airport;
import com.example.project.interfaces.IGet;
import com.example.project.interfaces.IInfo;
import org.springframework.stereotype.Service;

@Service
public class AirportDTO implements IGet<Airport> {
    @Override
    public IInfo getBasic(Airport airport) {
        return new AirportBasicDTO(airport);
    }

    @Override
    public IInfo getOwner(Airport airport) {
        return new AirportOwnerDTO(airport);
    }
}
